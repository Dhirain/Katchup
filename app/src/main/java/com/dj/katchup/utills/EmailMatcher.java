package com.dj.katchup.utills;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

public class EmailMatcher {

    public static boolean isValidEmail(@NonNull String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();
    }
}
