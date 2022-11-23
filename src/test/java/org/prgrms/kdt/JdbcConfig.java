package org.prgrms.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.customer.JdbcCustomerManager;
import org.prgrms.kdt.voucher.JdbcVoucherManager;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;

@TestConfiguration
public class JdbcConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/voucher_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JdbcVoucherManager jdbcVoucherManager(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        return new JdbcVoucherManager(namedParameterJdbcTemplate, dataSource);
    }

    @Bean
    public JdbcCustomerManager jdbcCustomerManager(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        return new JdbcCustomerManager(namedParameterJdbcTemplate, dataSource);
    }
}
