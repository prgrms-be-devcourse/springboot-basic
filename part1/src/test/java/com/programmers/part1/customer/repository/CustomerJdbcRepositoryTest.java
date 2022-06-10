package com.programmers.part1.customer.repository;

import com.programmers.part1.domain.customer.Customer;
import com.programmers.part1.order.voucher.repository.VoucherJdbcRepository;
import com.programmers.part1.order.voucher.repository.VoucherRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.part1.customer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-customer_management")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return  new JdbcTemplate(dataSource);
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

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerJdbcRepository customerRepository;

    EmbeddedMysql embeddedMysql;

    Customer baseCustomer;

    @BeforeAll
    void setup(){
        baseCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("test-user")
                .email("test1-user@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();

        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-customer_management",classPathScript("schema.sql"))
                .start();

        customerRepository.deleteAll();
    }

    @AfterAll
    void cleanup(){
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("고객 저장 테스트")
    void testSave() {

        customerRepository.save(baseCustomer);

        Optional<Customer> maybeCustomer = customerRepository.findById(baseCustomer.getCustomerId());

        assertThat(maybeCustomer.isEmpty(),is(false));
        assertThat(maybeCustomer.get(),samePropertyValuesAs(baseCustomer));
    }


    @Test
    @DisplayName("모든 고객 조회하기")

    void testFindAll() {
        assertThat(customerRepository.findAllCustomer().isEmpty(),is(true));

        customerRepository.save(baseCustomer);

        List<Customer> customerList = customerRepository.findAllCustomer();
        assertThat(customerList.isEmpty(),is(false));
        assertThat(customerList.size(),is(1));
    }

    @Test
    @DisplayName("고객 수정 테스트")
    void testUpdate() {

        customerRepository.save(baseCustomer);

        String updatedName = "update-user";
        String updatedEmail = "update@gmail.com";

        baseCustomer.changeName(updatedName);
        baseCustomer.changeEmail(updatedEmail);

        customerRepository.update(baseCustomer);

        Optional<Customer> maybeUpdateCustomer = customerRepository.findById(baseCustomer.getCustomerId());
        assertThat(maybeUpdateCustomer.isEmpty(),is(false));

        Customer updateCustomer = maybeUpdateCustomer.get();

        // 올바르게 수정 되었는지
        assertThat(baseCustomer, samePropertyValuesAs(updateCustomer));
    }

    @Test
    @DisplayName("특정 고객 삭제 테스트")
    void testDelete() {
        customerRepository.save(baseCustomer);

        Optional<Customer> maybeCustomer = customerRepository.findById(baseCustomer.getCustomerId());

        assertThat(maybeCustomer.isEmpty(),is(false));
        assertThat(maybeCustomer.get(),samePropertyValuesAs(baseCustomer));

        //없는 고객 삭제 실패
        customerRepository.deleteById(UUID.randomUUID());
        assertThat(customerRepository.findAllCustomer().isEmpty(),is(false));

        //고객 삭제 성공
        customerRepository.deleteById(baseCustomer.getCustomerId());
        assertThat(customerRepository.findAllCustomer().isEmpty(),is(true));

    }

}