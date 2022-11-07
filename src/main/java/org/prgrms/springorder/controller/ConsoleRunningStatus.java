package org.prgrms.springorder.controller;

public class ConsoleRunningStatus {

    private static boolean isRunning = true;

    private ConsoleRunningStatus() {
    }

    public static void stop() {
        isRunning = false;
    }

    public static boolean isRunning() {
        return isRunning;
    }

}
