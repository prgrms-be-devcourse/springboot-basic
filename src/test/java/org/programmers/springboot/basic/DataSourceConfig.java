package org.programmers.springboot.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class DataSourceConfig {

    @Autowired
    private DataSourceProperty dataSourceProperty;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(dataSourceProperty.getDriverClassName())
                .url(dataSourceProperty.getUrl())
                .username(dataSourceProperty.getUsername())
                .password(dataSourceProperty.getPassword())
                .build();
    }
}
