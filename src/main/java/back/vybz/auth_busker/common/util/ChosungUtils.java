package back.vybz.auth_busker.common.util;

public class ChosungUtils {

    private static final char[] CHOSUNG_LIST = {
            'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ',
            'ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };

    /**
     * 한글 문자열을 초성 문자열로 변환
     */
    public static String toChosung(String str) {
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch >= '가' && ch <= '힣') {
                int unicode = ch - '가';
                int chosungIndex = unicode / (21 * 28);
                sb.append(CHOSUNG_LIST[chosungIndex]);
            } else if (Character.isLetterOrDigit(ch)) {
                sb.append(ch);  // 영어/숫자는 그대로 유지
            }
            // 특수문자, 공백 등은 무시
        }
        return sb.toString();
    }

    /**
     * 입력값이 초성으로만 이루어져 있는지 판별
     */
    public static boolean isChosung(String str) {
        return str.matches("^[ㄱ-ㅎ]+$");
    }
}
