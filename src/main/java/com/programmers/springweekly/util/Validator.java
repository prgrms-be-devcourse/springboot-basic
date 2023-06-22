package com.programmers.springweekly.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern pattern = Pattern.compile("[0-9]+");

    public static void fixedAmountValidate(String fixedAmount){
        isNumber(fixedAmount);
    }

    public static void percentValidate(String percent){
        isNumber(percent);
        isRange(percent);
    }

    private static void isNumber(String number){
       Matcher match = pattern.matcher(number);

       if(!match.matches()){
           throw new IllegalArgumentException("Is not a number.");
       }
    }

    private static void isRange(String inputPercent){
        int percent = Integer.parseInt(inputPercent);

        if(percent > 100 && percent < 0){
            throw new IllegalArgumentException("A number that is out of range.");
        }
    }
}
