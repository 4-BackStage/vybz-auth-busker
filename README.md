# VYBZ Auth Busker Service

VYBZ 플랫폼의 버스커 인증 및 회원 관리를 담당하는 마이크로서비스입니다.

## 📋 목차

-   [개요](#개요)
-   [기술 스택](#기술-스택)
-   [주요 기능](#주요-기능)
-   [프로젝트 구조](#프로젝트-구조)
-   [API 문서](#api-문서)
-   [설치 및 실행](#설치-및-실행)
-   [환경 설정](#환경-설정)
-   [인증 시스템](#인증-시스템)
-   [이벤트 처리](#이벤트-처리)

## 🎯 개요

VYBZ Auth Busker Service는 다음과 같은 기능을 제공합니다:

-   **버스커 회원가입**: 이메일, 전화번호 인증을 통한 회원가입
-   **버스커 로그인**: 이메일/비밀번호 기반 로그인
-   **JWT 토큰 관리**: Access Token, Refresh Token 발급 및 검증
-   **이메일 인증**: Gmail SMTP를 통한 이메일 인증 코드 발송
-   **SMS 인증**: CoolSMS를 통한 전화번호 인증 코드 발송
-   **토큰 재발급**: Refresh Token을 통한 Access Token 재발급
-   **로그아웃**: 토큰 무효화 및 세션 관리
-   **이벤트 처리**: Kafka를 통한 버스커 생성 이벤트 발행

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=for-the-badge)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Database & Cache

-   **MySQL 8.0**: 버스커 정보 및 인증 데이터 저장
-   **Redis**: Refresh Token 저장 및 세션 관리

### Message Queue

-   **Apache Kafka**: 비동기 이벤트 처리

### External Services

-   **Gmail SMTP**: 이메일 인증 코드 발송
-   **CoolSMS**: SMS 인증 코드 발송

### Documentation

-   **Swagger/OpenAPI 3.0**: API 문서화

### Build & Deploy

-   **Gradle**: 빌드 도구
-   **Docker**: 컨테이너화

## 🚀 주요 기능

### 1. 버스커 회원가입 (`/api/v1/busker/sign-up`)

-   **이메일 인증**: 이메일 인증 코드 발송 및 검증
-   **전화번호 인증**: SMS 인증 코드 발송 및 검증
-   **비밀번호 암호화**: BCrypt를 통한 안전한 비밀번호 저장
-   **중복 검증**: 이메일, 전화번호 중복 확인

### 2. 버스커 로그인 (`/api/v1/busker/sign-in`)

-   **이메일/비밀번호 로그인**: 기존 회원 로그인
-   **JWT 토큰 발급**: Access Token, Refresh Token 생성
-   **로그인 검증**: 이메일, 비밀번호 유효성 검증

### 3. 토큰 관리

-   **Access Token**: API 접근을 위한 JWT 토큰 (60일 유효)
-   **Refresh Token**: Access Token 재발급을 위한 토큰 (15일 유효)
-   **토큰 검증**: JWT 서명 및 만료 시간 검증
-   **토큰 재발급**: Refresh Token을 통한 Access Token 재발급

### 4. 인증 및 보안

-   **JWT 인증 필터**: 모든 API 요청에 대한 JWT 토큰 검증
-   **Spring Security**: 인증 및 권한 관리
-   **CORS 설정**: 크로스 오리진 요청 허용
-   **세션 관리**: Stateless 세션 관리

### 5. 이메일 인증

-   **인증 코드 발송**: Gmail SMTP를 통한 이메일 발송
-   **인증 코드 검증**: Redis에 저장된 인증 코드 검증
-   **템플릿 기반**: HTML 템플릿을 통한 이메일 발송

### 6. SMS 인증

-   **인증 코드 발송**: CoolSMS API를 통한 SMS 발송
-   **인증 코드 검증**: Redis에 저장된 인증 코드 검증
-   **발송 제한**: 인증 코드 발송 횟수 제한

### 7. 로그아웃

-   **토큰 무효화**: Refresh Token Redis에서 삭제
-   **세션 종료**: 사용자 세션 완전 종료

## 📁 프로젝트 구조

```
src/main/java/back/vybz/auth_busker/
├── common/                    # 공통 모듈
│   ├── application/          # 공통 서비스
│   │   ├── ReissueService.java
│   │   └── TokenService.java
│   ├── config/               # 설정 클래스들
│   │   ├── ApplicationConfig.java
│   │   ├── CoolSmsConfig.java
│   │   ├── RedisConfig.java
│   │   ├── RestTemplateConfig.java
│   │   ├── SecurityConfig.java
│   │   └── SwaggerConfig.java
│   ├── entity/               # 공통 엔티티
│   │   ├── BaseEntity.java
│   │   ├── BaseResponseEntity.java
│   │   ├── BaseResponseStatus.java
│   │   └── SoftDeletableEntity.java
│   ├── exception/            # 예외 처리
│   │   ├── AsyncExceptionHandler.java
│   │   ├── BaseException.java
│   │   ├── BaseExceptionHandler.java
│   │   └── BaseExceptionHandlerFilter.java
│   ├── jwt/                  # JWT 관련
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtProvider.java
│   ├── pattern/              # 정규식 패턴
│   │   └── RegexPatterns.java
│   └── util/                 # 유틸리티
│       ├── ChosungUtils.java
│       └── RedisUtil.java
├── kafka/                    # Kafka 이벤트 처리
│   ├── config/               # Kafka 설정
│   │   ├── BuskerAuthKafkaConfig.java
│   │   └── BuskerSearchKafkaConfig.java
│   ├── event/                # 이벤트 모델
│   │   ├── BuskerAuthEvent.java
│   │   └── BuskerSearchEvent.java
│   └── producer/             # 이벤트 프로듀서
│       ├── BuskerKafkaProducer.java
│       └── BuskerSearchKafkaProducer.java
└── busker/                   # 버스커 도메인
    ├── application/          # 버스커 서비스 로직
    │   ├── AuthService.java
    │   ├── AuthServiceImpl.java
    │   ├── EmailService.java
    │   ├── EmailServiceImpl.java
    │   ├── SmsService.java
    │   └── SmsServiceImpl.java
    ├── domain/               # 버스커 도메인 모델
    │   ├── Busker.java
    │   ├── CustomBuskerDetails.java
    │   └── Status.java
    ├── dto/                  # 버스커 DTO
    │   ├── request/
    │   │   ├── RequestAgreementConsentDto.java
    │   │   ├── RequestAuthSignInDto.java
    │   │   ├── RequestEmailFindDto.java
    │   │   ├── RequestExistsEmailDto.java
    │   │   ├── RequestSendEmailCodeDto.java
    │   │   ├── RequestSendSmsCodeDto.java
    │   │   ├── RequestSignUpDto.java
    │   │   ├── RequestVerificationEmailDto.java
    │   │   └── RequestVerificationSmsDto.java
    │   ├── response/
    │   │   └── ResponseBuskerSignInDto.java
    │   └── SendPurpose.java
    ├── infrastructure/       # 버스커 리포지토리
    │   ├── AuthRepository.java
    │   └── email/
    │       ├── EmailSender.java
    │       └── EmailTemplateBuilder.java
    ├── presentation/         # 버스커 컨트롤러
    │   ├── AuthController.java
    │   ├── EmailController.java
    │   └── SmsController.java
    ├── util/                 # 버스커 유틸리티
    │   ├── SmsCertificationUtil.java
    │   ├── VerificationKeyManager.java
    │   └── VerificationValidator.java
    └── vo/                   # 버스커 VO
        ├── request/
        │   ├── RequestAgreementConsentVo.java
        │   ├── RequestAuthSignInVo.java
        │   ├── RequestExistsEmailVo.java
        │   ├── RequestSendEmailCodeVo.java
        │   ├── RequestSendSmsCodeVo.java
        │   ├── RequestSignUpVo.java
        │   ├── RequestVerificationEmailVo.java
        │   └── RequestVerificationSmsVo.java
        └── response/
            └── ResponseBuskerSignInVo.java
```

## 📚 API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다:

-   **URL**: `http://localhost:8000/busker-auth-service/swagger-ui/index.html`
-   **API 그룹**: Auth-service, Email-service, SMS-service

### 주요 API 엔드포인트

#### 인증 API

-   `POST /api/v1/busker/sign-up` - 버스커 회원가입
-   `POST /api/v1/busker/sign-in` - 버스커 로그인
-   `POST /api/v1/busker/reissue` - Access Token 재발급
-   `POST /api/v1/busker/sign-out` - 로그아웃

#### 이메일 인증 API

-   `POST /api/v1/busker/email-code` - 이메일 인증 코드 발송
-   `POST /api/v1/busker/email-verify` - 이메일 인증 코드 검증

#### SMS 인증 API

-   `POST /api/v1/busker/sms-code` - SMS 인증 코드 발송
-   `POST /api/v1/busker/sms-verify` - SMS 인증 코드 검증

### API 요청/응답 예시

#### 버스커 회원가입 요청

```json
{
    "email": "busker@example.com",
    "phoneNumber": "01012345678",
    "password": "password123",
    "nickname": "버스커닉네임",
    "profileImageUrl": "https://example.com/profile.jpg",
    "introduction": "안녕하세요! 버스커입니다.",
    "categoryId": [1, 2],
    "agreements": [
        {
            "agreementId": 1,
            "consent": true
        },
        {
            "agreementId": 2,
            "consent": true
        }
    ]
}
```

#### 버스커 로그인 요청

```json
{
    "email": "busker@example.com",
    "password": "password123"
}
```

#### 버스커 로그인 응답

```json
{
    "status": "SUCCESS",
    "message": "요청이 성공적으로 처리되었습니다.",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "buskerUuid": "550e8400-e29b-41d4-a716-446655440000"
    }
}
```

#### 이메일 인증 코드 발송 요청

```json
{
    "email": "busker@example.com",
    "sendPurpose": "SIGN_UP"
}
```

#### SMS 인증 코드 발송 요청

```json
{
    "phoneNumber": "01012345678",
    "sendPurpose": "SIGN_UP"
}
```

## 🚀 설치 및 실행

### 1. 사전 요구사항

-   Java 17
-   Gradle 8.4+
-   Docker (선택사항)
-   MySQL 8.0
-   Redis
-   Kafka

### 2. 로컬 실행

```bash
# 프로젝트 클론
git clone <repository-url>
cd auth-busker

# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. Docker 실행

```bash
# Docker 이미지 빌드
docker build -t vybz-auth-busker .

# Docker 컨테이너 실행
docker run -p 8000:8000 vybz-auth-busker
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정
-   `application-dev.yml`: 개발 환경 설정
-   `application-db.yml`: 데이터베이스 설정

### 환경 변수

```yaml
# 데이터베이스 설정
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/auth_busker?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver

# Redis 설정
spring:
  data:
    redis:
      host: ${REDIS_HOST}
      port: ${REDIS_PORT}
      password: ${REDIS_PASSWORD}

# Kafka 설정
spring:
  kafka:
    bootstrap-servers: ${KAFKA_SERVERS}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

# JWT 설정
JWT:
  secret-key: ${JWT_SECRET_KEY}
  token:
    access-expire-time: ${JWT_ACCESS_EXPIRE_TIME}
    refresh-expire-time: ${JWT_REFRESH_EXPIRE_TIME}

# 이메일 설정
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${EMAIL_USERNAME}
    password: ${EMAIL_PASSWORD}

# CoolSMS 설정
cools:
  api:
    key: ${COOLSMS_API_KEY}
    secret: ${COOLSMS_API_SECRET}
    sender-number: ${COOLSMS_SENDER_NUMBER}

# Eureka 설정
eureka:
  client:
    service-url:
      defaultZone: ${EUREKA_SERVER_URL}
```

## 🔐 인증 시스템

### JWT 토큰 구조

#### Access Token

```json
{
    "busker_uuid": "550e8400-e29b-41d4-a716-446655440000",
    "token_type": "access",
    "iat": 1640995200,
    "exp": 1640997000
}
```

#### Refresh Token

```json
{
    "sub": "550e8400-e29b-41d4-a716-446655440000",
    "token_type": "refresh",
    "iat": 1640995200,
    "exp": 1642125600
}
```

### 인증 플로우

1. **회원가입**: 이메일/SMS 인증 → 비밀번호 설정 → 회원 등록
2. **로그인**: 이메일/비밀번호 입력 → 사용자 검증 → 토큰 발급
3. **Redis 저장**: Refresh Token을 Redis에 저장 (15일)
4. **API 요청**: Access Token으로 API 호출
5. **토큰 검증**: JWT 필터에서 토큰 유효성 검증
6. **토큰 재발급**: Refresh Token으로 Access Token 재발급

### 보안 설정

-   **CORS**: 모든 오리진 허용 (개발 환경)
-   **CSRF**: 비활성화 (JWT 기반 인증)
-   **세션**: Stateless 세션 관리
-   **헤더**: Authorization 헤더를 통한 토큰 전달

## 📡 이벤트 처리

### Kafka 이벤트

#### 발행 이벤트

-   **BuskerAuthEvent**: 버스커 생성 이벤트

    -   `buskerUuid`: 버스커 UUID
    -   `categoryId`: 카테고리 ID 목록
    -   `nickname`: 버스커 닉네임
    -   `agreements`: 약관 동의 정보
    -   `profileImageUrl`: 프로필 이미지 URL
    -   `introduction`: 자기소개

-   **BuskerSearchEvent**: 버스커 검색 이벤트
    -   `buskerUuid`: 버스커 UUID
    -   `nickname`: 버스커 닉네임
    -   `nicknameChosung`: 닉네임 초성
    -   `profileImageUrl`: 프로필 이미지 URL

### 이벤트 프로듀서

-   `BuskerKafkaProducer`: 버스커 생성 이벤트 발행
-   `BuskerSearchKafkaProducer`: 버스커 검색 이벤트 발행

### Kafka 토픽

-   `create-busker-auth`: 버스커 생성 이벤트 토픽
-   `create-busker-search`: 버스커 검색 이벤트 토픽

### 이벤트 발행 시점

-   **신규 버스커 등록**: 회원가입 완료 후
-   **이벤트 데이터**: 버스커 UUID, 닉네임, 프로필 이미지 URL, 카테고리 정보

## 🏗 아키텍처

### 도메인 주도 설계 (DDD)

-   **Domain Layer**: 버스커 도메인 모델과 비즈니스 로직
-   **Application Layer**: 인증 서비스 로직과 유스케이스
-   **Infrastructure Layer**: 데이터베이스 접근과 외부 시스템 연동
-   **Presentation Layer**: REST API 엔드포인트

### 마이크로서비스 패턴

-   **Service Discovery**: Eureka Client를 통한 서비스 등록
-   **Event-Driven**: Kafka를 통한 비동기 이벤트 처리
-   **Stateless**: 상태 없는 서비스 설계

### 데이터베이스 설계

-   **MySQL**: 버스커 정보, 인증 데이터
-   **Redis**: Refresh Token 저장 및 세션 관리

## 🔧 개발 가이드

### 코드 컨벤션

-   **패키지 구조**: 도메인별 계층 분리
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

### 외부 서비스 설정

#### Gmail SMTP 설정

1. Gmail 계정에서 2단계 인증 활성화
2. 앱 비밀번호 생성
3. 환경 변수에 이메일 정보 설정

#### CoolSMS 설정

1. CoolSMS 개발자 계정 생성
2. API Key, Secret 발급
3. 발신번호 등록
4. 환경 변수에 CoolSMS 정보 설정

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team

---

**VYBZ Auth Busker Service** - 안전하고 편리한 버스커 인증 서비스
