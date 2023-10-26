package org.programmers.springorder.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @Configuration
    static class Config{
        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_voucher")
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
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }

    }

    @Autowired
    CustomerRepository customerRepository;

    @BeforeAll
    static void setUp(){
        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_voucher", classPathScript("/schema.sql"))
                .start();
    }

    @AfterEach
    void clear(){
        embeddedMysql.executeScripts("test_voucher", List.of(() ->"delete from vouchers; delete from customers;"));
    }

    @Test
    @DisplayName("회원 저장에 성공한다.")
    void save() {
        // given
        int currentSize = customerRepository.findAll().size();
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "test1", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "test2", CustomerType.NORMAL);
        Customer customer3 = Customer.toCustomer(UUID.randomUUID(), "test3", CustomerType.BLACK);

        // when
        Customer insertedCustomer1 = customerRepository.insert(customer1);
        Customer insertedCustomer2 = customerRepository.insert(customer2);
        Customer insertedCustomer3 = customerRepository.insert(customer3);

        // then
        List<Customer> insertedCustomers = Arrays.asList(insertedCustomer1, insertedCustomer2, insertedCustomer3);
        List<Customer> all = customerRepository.findAll();

        assertThat(all).hasSize(currentSize + 3);
        assertThat(all).containsAll(insertedCustomers);

    }

    @Test
    @DisplayName("전체 회원 조회에 성공한다.")
    void findAll() {
        // given
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "test1", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "test2", CustomerType.NORMAL);
        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

        // when
        List<Customer> customerList = customerRepository.findAll();

        // then
        assertThat(customerList).hasSize(2);
    }

    @Test
    @DisplayName("블랙 리스트 조회 테스트")
    void findAllBlackList() {
        // given
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "test1", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "test2", CustomerType.BLACK);
        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

        // when
        List<Customer> customerList = customerRepository.findAllBlackList();

        // then
        assertThat(customerList).hasSize(1);
        assertThat(customerList).contains(customer2);
    }

}