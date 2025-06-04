package back.vybz.auth_busker.common.pattern;

public class RegexPatterns {
    public static final String PASSWORD =
            "^(?=.*[A-Za-z])" +
                    "(?=.*\\d)" +
                    "(?=.*[!^&*/?])" +
                    ".{8,20}$";


    public static final String PHONE_NUMBER =
            "^010-\\d{4}-\\d{4}$";


    public static final String NICKNAME =
            "^[가-힣a-zA-Z0-9]{5,20}$";

    public static final String EMAIL =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
}