package me.programmers.springboot.basic.springbootbasic.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcConfig {
    @Bean
    public DataSource dataSource() {

        var datasource =  DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/order_mgmt")
                .username("programmers")
                .password("programmers")
                .type(HikariDataSource.class)
                .build();
        datasource.setMaximumPoolSize(1000);
        datasource.setMinimumIdle(100);
        return datasource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
