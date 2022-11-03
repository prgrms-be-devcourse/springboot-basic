package org.prgrms.kdtspringdemo.commandline_application;



public enum Command {
    EXIT, CREATE, LIST, ERROR;

    public static Command getCommand(String string) {
        try {
            return Command.valueOf(string.toUpperCase());
        } catch (Exception e) {
            return ERROR;
        }
    }
}
