package org.prgrms.kdt.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yhh1056
 * Date: 2021/09/05 Time: 12:24 오전
 */

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.kdt.api", "org.prgrms.kdt.aop"}
)
public class WebConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
