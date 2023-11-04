package com.programmers.springbootbasic.util;

import com.programmers.springbootbasic.common.TimeGenerator;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ProdTimeGenerator implements TimeGenerator {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
