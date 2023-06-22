package org.promgrammers.springbootbasic.controller;

public class CommandProgramStatus {

    private static boolean isRunning = true;

    private CommandProgramStatus() throws InstantiationException {
        throw new InstantiationException("인스턴스화 할 수 없습니다.");
    }

    public static void stop() {
        isRunning = false;
    }

    public static boolean isRunning() {
        return isRunning;
    }
}