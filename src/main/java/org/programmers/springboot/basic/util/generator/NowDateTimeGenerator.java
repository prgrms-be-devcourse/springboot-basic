package org.programmers.springboot.basic.util.generator;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NowDateTimeGenerator implements DateTimeGenerator {

    @Override
    public LocalDateTime generateDateTime() {
        return LocalDateTime.now();
    }
}
