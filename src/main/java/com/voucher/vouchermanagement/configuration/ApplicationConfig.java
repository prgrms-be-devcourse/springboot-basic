package com.voucher.vouchermanagement.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Value("${db.connection.name}")
    private String dbUserName;
    @Value("${db.connection.password}")
    private String dbPassword;
    @Value("${db.connection.url}")
    private String dbUrl;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .url(dbUrl)
                .username(dbUserName)
                .password(dbPassword)
                .type(HikariDataSource.class)
                .build();

        return dataSource;
    }
}
