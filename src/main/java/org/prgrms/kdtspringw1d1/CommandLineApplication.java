package org.prgrms.kdtspringw1d1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLineApplication {

    public static enum CommandType {
        EXIT,
        CREATE,
        LIST
    }

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String noticeText = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.";

    public static void main(String[] args) {

    }
}
