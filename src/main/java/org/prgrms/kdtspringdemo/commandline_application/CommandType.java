package org.prgrms.kdtspringdemo.commandline_application;



public enum CommandType {
    EXIT, CREATE, LIST, ERROR;

    public static CommandType getTypeByName(String string) {
        try {
            return CommandType.valueOf(string.toUpperCase());
        } catch (Exception e) {
            return ERROR;
        }
    }
}
