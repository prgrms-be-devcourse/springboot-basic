package org.prgrms.orderApp.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.orderApp.customer.CustomerNameJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherNameJdbcRepositoryTest {

    private static Logger logger = LoggerFactory.getLogger(VoucherNameJdbcRepositoryTest.class);


    @Configuration
    @EnableTransactionManagement
    @ActiveProfiles("local")
    @ComponentScan( basePackages = {"org.prgrms.orderApp.voucher"})
    static class Config{
        @Bean
        public DataSource dataSource(){

            var dataSource =  DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
//            dataSource.setMaximumPoolSize(100);
//            dataSource.setMinimumIdle(13);
            return dataSource;

        }
        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }
        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
            return new TransactionTemplate(platformTransactionManager);
        }


    }

    @Autowired
    VoucherNameJdbcRepository voucherNameJdbcRepository;

    @Autowired
    DataSource dataSource;

    @Test
    @DisplayName("FixedAmountData를 저장할 수 있다. ")
    void insertFixedAmountData() throws IOException {
        var insertResult = voucherNameJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 20));
        assertThat(insertResult, is(1));
    }

    @Test
    @DisplayName("PercentAmountData를 저장할 수 있다. ")
    void insertPercentAmountData() throws IOException {
        var insertResult = voucherNameJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 50));
        assertThat(insertResult, is(1));
    }

    @Test
    @DisplayName("모든 고객 정보를 얻을 수 있다. ")
    void findAll() throws IOException, ParseException {
        var findData = voucherNameJdbcRepository.findAll();
        assertThat(findData.isEmpty(), is(false));
        findData.forEach(v->System.out.println(v));

    }
}