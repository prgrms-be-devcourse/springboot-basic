package org.programmers.springboot.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class DataSourceConfig {

    @Value(("${spring.datasource.driver-class-name}"))
    private String driverClassName;

    @Value(("${spring.datasource.url}"))
    private String url;

    @Value(("${spring.datasource.username}"))
    private String username;

    @Value(("${spring.datasource.password}"))
    private String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
