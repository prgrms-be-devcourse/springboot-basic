package org.prgrms.orderApp.util.library;


import java.util.regex.Pattern;

public class Validate {

    public static boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }
}
