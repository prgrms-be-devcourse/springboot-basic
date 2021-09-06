package org.prgrms.orderApp.util.library;


public class Validate {

    public static void checkBlankForString(String valueName, String valueToCheck){
        if (valueToCheck.isBlank()) throw new RuntimeException("%s should not be blank".formatted(valueName));
    }
}
