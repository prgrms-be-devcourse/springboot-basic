package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.util.DatabaseProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    private final DatabaseProperties dbConfigProperties;

    public DatabaseConfiguration(DatabaseProperties dbConfigProperties) {
        this.dbConfigProperties = dbConfigProperties;
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(dbConfigProperties.getUrl())
                .username(dbConfigProperties.getUsername())
                .password(dbConfigProperties.getPassword())
                .type(HikariDataSource.class) //DataSource 생성에 사용할 구현체 지정
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
