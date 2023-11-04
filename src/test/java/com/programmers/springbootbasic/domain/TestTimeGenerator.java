package com.programmers.springbootbasic.domain;

import com.programmers.springbootbasic.common.TimeGenerator;
import java.time.LocalDateTime;

public class TestTimeGenerator implements TimeGenerator {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.of(2023, 10, 27, 15, 30, 0, 0);
    }
}
