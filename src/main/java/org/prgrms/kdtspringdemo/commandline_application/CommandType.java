package org.prgrms.kdtspringdemo.commandline_application;


public enum CommandType {
    EXIT, CREATE, LIST,ERROR ;

    public static CommandType getTypeByName(String string) throws IllegalArgumentException {
        try{
            return CommandType.valueOf(string.toUpperCase());

        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("잘못된 커맨드 타입 입니다.");
        }
    }
}
