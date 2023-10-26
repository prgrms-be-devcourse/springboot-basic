package com.programmers.vouchermanagement.wallet;

import com.programmers.vouchermanagement.wallet.repository.WalletJDBCRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletJDBCRepositoryTest {
    @Autowired
    WalletJDBCRepository walletJDBCRepository;
    @Autowired
    DataSource dataSource;

    @Test
    @DisplayName("고객 id와 바우처 id 정보를 함께 저장할 수 있다.")
    void save() {
    }

    @Test
    @DisplayName("고객 id로 고객이 가진 바우처 id를 가져올 수 있다.")
    void findAllVoucherByCustomerId() {
    }

    @Test
    @DisplayName("함께 저장된 고객 id와 바우처 id 정보를 삭제할 수 있다.")
    void delete() {
    }

    @Test
    @DisplayName("바우처 id로 바우처를 가진 고객 id를 가져올 수 있다.")
    void findCustomerByVoucherId() {
    }

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.vouchermanagement.wallet.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/test")
                    .username("root")
                    .password("980726")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }
}