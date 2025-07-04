package com.example.base_jsp.utils;

public class Regex {

    public static final String PHONE_VALIDATION = "(0[3|5|7|8|9])+([0-9]{8})";

    public static final String PASSWORD_VALIDATION ="^(?!.* ).{6,20}$";

    public final static String CAR_LICENSE_PLATES_REGEX = "([1-9][0-9])+([A-Z])+(-[0-9]{5})";

    public final static String CAR_START_YEAR_REGEX = "[13][0-9]{3}";

    public static final String SECURE_PASSWORD_VALIDATION = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^!&*()])[A-Za-z\\d@#$%^!&*()]{8,20}$";


}
