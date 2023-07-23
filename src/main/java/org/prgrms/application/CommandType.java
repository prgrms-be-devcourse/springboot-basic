//package org.prgrms.application;
//
//import java.util.Arrays;
//
//public enum CommandType {
//    EXIT,
//    CREATE,
//    LIST;
//
//    public static CommandType findBySelection(String selection) {
//        return Arrays.stream(values())
//                .filter(s -> s.name().equals(selection.toUpperCase()))
//                .findFirst()
//                .orElseThrow(()-> new IllegalArgumentException("잘못된 명령 입력입니다."));
//    }
//
//}
