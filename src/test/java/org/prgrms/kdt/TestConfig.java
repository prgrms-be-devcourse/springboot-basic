package org.prgrms.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.customer.CustomerJdbcStorage;
import org.prgrms.kdt.storage.JdbcVoucherStorage;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/voucher_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(1000);
        dataSource.setMinimumIdle(100);
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JdbcVoucherStorage jdbcVoucherStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        return new JdbcVoucherStorage(namedParameterJdbcTemplate);
    }

    @Bean
    public CustomerJdbcStorage customerJdbcStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        return new CustomerJdbcStorage(namedParameterJdbcTemplate);
    }
}
