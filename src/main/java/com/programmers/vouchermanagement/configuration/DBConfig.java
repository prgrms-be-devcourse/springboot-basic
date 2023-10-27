package com.programmers.vouchermanagement.configuration;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.programmers.vouchermanagement.configuration.properties.datasource.DataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("jdbc")
public class DBConfig {
    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
