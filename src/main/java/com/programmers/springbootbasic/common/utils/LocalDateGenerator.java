package com.programmers.springbootbasic.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateGenerator implements LocalDateValueStrategy {
    @Override
    public LocalDate generateLocalDate() {
        return LocalDate.now();
    }
}
