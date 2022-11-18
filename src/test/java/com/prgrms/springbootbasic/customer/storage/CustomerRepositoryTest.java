package com.prgrms.springbootbasic.customer.storage;

import com.prgrms.springbootbasic.customer.domain.Customer;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;


import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(Lifecycle.PER_CLASS)
class CustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = "com.prgrms.springbootbasic.customer.storage"
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt?autoReconnect=true&verifyServerCertificate=false&useSSL=true")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    DataSource dataSource;

    private Customer customer;
    private List<Customer> customerList;

    @BeforeAll
    void setDatabase() {

        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order_mgmt", classPathScript("customer.sql"))
                .start();
    }

    @BeforeEach
    public void setup() {
        customer = new Customer(UUID.randomUUID(), "test", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerList = List.of(
                new Customer(UUID.randomUUID(), "test1", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)),
                new Customer(UUID.randomUUID(), "test2", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)),
                new Customer(UUID.randomUUID(), "test3", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
        );
    }

    @AfterEach
    private void deleteAll() {
        jdbcTemplate.update("DELETE FROM customer");
    }

    @Test
    @DisplayName("Customer를 저장할 수 있다.")
    void save() {
        //given&when
        Customer savedCustomer = jdbcCustomerRepository.save(customer);

        //then
        assertThat(savedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("id를 조회해서 Customer를 조회할 수 있다.")
    void findById() {
        //given
        Customer savedCustomer = jdbcCustomerRepository.save(customer);

        //when
        Optional<Customer> found = jdbcCustomerRepository.findById(savedCustomer.getId());

        //then
        assertThat(found.isPresent()).isTrue();
        Customer foundCustomer = found.get();

        assertThat(foundCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("모든 Customer를 조회할 수 있다.")
    void findAll() {
        //given
        customerList.forEach(jdbcCustomerRepository::save);

        //when
        List<Customer> foundCustomers = jdbcCustomerRepository.findAll();

        //then
        assertThat(customerList.size()).isEqualTo(foundCustomers.size());
    }

    @Test
    @DisplayName("Customer를 수정할 수 있다.")
    void update() {
        //given
        Customer savedCustomer = jdbcCustomerRepository.save(customer);

        //when
        String newName = "new_name";
        savedCustomer.update(newName);

        Customer updatedCustomer = jdbcCustomerRepository.update(savedCustomer);

        //then
        assertThat(savedCustomer.getId()).isEqualTo(updatedCustomer.getId());
        assertThat(savedCustomer.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Customer를 삭제할 수 있다.")
    void delete() {
        //given
        Customer savedCustomer = jdbcCustomerRepository.save(customer);
        List<Customer> foundBeforeDelete = jdbcCustomerRepository.findAll();

        //when
        jdbcCustomerRepository.delete(savedCustomer.getId());

        //then
        List<Customer> foundAfterDelete = jdbcCustomerRepository.findAll();
        assertThat(foundAfterDelete.size()).isEqualTo(foundBeforeDelete.size() - 1);
    }
}