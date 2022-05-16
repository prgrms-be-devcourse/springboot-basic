package com.programmers.springbootbasic.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertiesFactory.class)
@Getter
@Setter
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;

}
