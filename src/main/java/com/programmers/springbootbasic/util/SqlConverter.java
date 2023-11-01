package com.programmers.springbootbasic.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlConverter {

    public static LocalDateTime toLocalDateTime(String sqlDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(sqlDateTime, formatter);
    }

}
