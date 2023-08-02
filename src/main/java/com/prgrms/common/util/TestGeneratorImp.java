package com.prgrms.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestGeneratorImp implements Generator {

    @Override
    public String makeKey() {
        return "22";
    }

    @Override
    public LocalDateTime makeDate() {
        String str = "2023-07-29 13:47:13.248";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(str, formatter);
    }
}
