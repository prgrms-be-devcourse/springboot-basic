package org.prgrms.dev;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:application.yml")
@Component
class CommandLineApplicationTests {

    @Test
    void contextLoads() {
    }

}
