package org.prgrms.kdt.config;

import javax.sql.DataSource;

import org.prgrms.kdt.voucher.repository.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@TestConfiguration
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher",
        "org.prgrms.kdt.controller",
        "org.prgrms.kdt.customer"})
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        return  DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
            .username("test")
            .password("test1234!")
            .build();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new JdbcVoucherRepository(jdbcTemplate());
    }

}
