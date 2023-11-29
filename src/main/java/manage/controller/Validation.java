package manage.controller;

import java.util.regex.Pattern;

public class Validation {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_PATTERN = "^[0-9]{10}$";
    private static final Pattern EmailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern PhonePattern = Pattern.compile(PHONE_PATTERN);

    public static boolean EmailValidate(String email) {
        return EmailPattern.matcher(email).matches();
    }

    public static boolean PhoneValidate(String phone) {
        return PhonePattern.matcher(phone).matches();
    }
}
