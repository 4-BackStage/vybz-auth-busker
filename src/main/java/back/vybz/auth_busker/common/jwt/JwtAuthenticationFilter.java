package back.vybz.auth_busker.common.jwt;

import back.vybz.auth_busker.busker.application.AuthService;
import back.vybz.auth_busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.common.exception.BaseException;
import back.vybz.auth_busker.common.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    private final AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        System.out.println("1");

        final String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {

            jwtProvider.validateToken(jwt);

            String tokenType = jwtProvider.extractTokenType(jwt);
            String buskerUuid = jwtProvider.extractClaim(jwt, claims -> claims.get("busker_uuid", String.class));

            if (buskerUuid != null && "access".equals(tokenType)) {
                String redisKey = "Access_busker:" + buskerUuid;
                String redisAccessToken = redisUtil.get(redisKey);

                if (redisAccessToken == null || !redisAccessToken.equals(jwt)) {
                    throw new BaseException(BaseResponseStatus.TOKEN_MISMATCH_WITH_REDIS);
                }

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = authService.loadBuskerByUuid(buskerUuid);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.warn("JWT 인증 필터 오류: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
