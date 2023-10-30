package com.programmers.vouchermanagement.infra.io;

public class ConsoleOutput {
    public static void printHelp() {
        System.out.println("""
                
                Global -------------------------
                         📍exit
                Voucher ------------------------
                         📍create
                         📍list
                         📍detail
                         📍update
                         📍delete
                Customer -----------------------
                         📍blacklist
                Wallet -------------------------
                         ❕Not implemented yet
                """);
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
