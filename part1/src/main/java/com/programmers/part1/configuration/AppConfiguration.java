package com.programmers.part1.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(
        basePackages = "com.programmers.part1"
)
@EnableConfigurationProperties
public class AppConfiguration {
    @Bean
    public DataSource dataSource() {
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/customer_voucher_management")
                .username("root")
                .password("jungki503\\")
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(1000);
        dataSource.setMinimumIdle(100);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }
}
