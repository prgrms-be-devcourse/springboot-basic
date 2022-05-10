package org.programmers.kdt.weekly.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan(basePackages = {"org.programmers.kdt.weekly.*"})
public class DbConfig {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String passWord;

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(url)
            .username(userName)
            .password(passWord)
            .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}