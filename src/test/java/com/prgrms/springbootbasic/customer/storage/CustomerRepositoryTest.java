package com.prgrms.springbootbasic.customer.storage;

import com.prgrms.springbootbasic.common.exception.DataModifyingException;
import com.prgrms.springbootbasic.customer.domain.Customer;
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
import org.springframework.dao.DataIntegrityViolationException;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(Lifecycle.PER_CLASS)
class CustomerRepositoryTest {

    private static final String tooLongName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

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
    private Customer wrongNameCustomer;
    private List<Customer> customerList;

    @BeforeAll
    public void setDatabase() {

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
        customer = new Customer(UUID.randomUUID(), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), "test");
        wrongNameCustomer = new Customer(UUID.randomUUID(),
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                tooLongName);
        customerList = List.of(
                new Customer(UUID.randomUUID(), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), "test1"),
                new Customer(UUID.randomUUID(), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), "test2"),
                new Customer(UUID.randomUUID(), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), "test3")
        );
    }

    @AfterEach
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customer");
    }

    @Test
    @DisplayName("Customer를 저장할 수 있다.")
    void save() {
        //given&when
        jdbcCustomerRepository.save(customer);
        Optional<Customer> found = jdbcCustomerRepository.findById(customer.getId());

        //then
        assertThat(found.isPresent()).isTrue();
        Customer foundCustomer = found.get();

        assertThat(foundCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("영문 기준 50글자를 초과하면 저장에 실패한다.")
    void saveFailStringTooLong() {
        //when&then
        assertThrows(DataIntegrityViolationException.class, () -> jdbcCustomerRepository.save(wrongNameCustomer));
    }

    @Test
    @DisplayName("id를 조회해서 Customer를 조회할 수 있다.")
    void findById() {
        //given
        jdbcCustomerRepository.save(customer);

        //when
        Optional<Customer> found = jdbcCustomerRepository.findById(customer.getId());

        //then
        assertThat(found.isPresent()).isTrue();

        assertThat(found.get())
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
    @DisplayName("이름으로 Customer를 조회할 수 있다.")
    void findByName() {
        //given
        jdbcCustomerRepository.save(customer);

        //when
        Optional<Customer> foundCustomer = jdbcCustomerRepository.findByName(customer.getName());

        //then
        assertThat(foundCustomer.isPresent()).isTrue();
        assertThat(foundCustomer.get())
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("Customer를 수정할 수 있다.")
    void update() {
        //given
        jdbcCustomerRepository.save(customer);

        //when
        String newName = "new_name";
        customer.update(newName);

        jdbcCustomerRepository.update(customer);
        Optional<Customer> found = jdbcCustomerRepository.findById(customer.getId());

        //then
        assertThat(found.isPresent()).isTrue();

        assertThat(found.get())
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("수정하고자 하는 이름이 너무 길면 Customer를 수정할 수 없다.")
    void updateFailStringTooLong() {
        //given
        jdbcCustomerRepository.save(customer);


        //when&then
        customer.update(tooLongName);
        assertThrows(DataIntegrityViolationException.class, () -> jdbcCustomerRepository.update(customer));
    }

    @Test
    @DisplayName("조회할 수 없는 PK값을 갖고있는 Customer를 update하려고 하면 실패한다.")
    void updateFailIdNotFound() {
        //given
        jdbcCustomerRepository.save(customer);

        //when&then
        Customer wrongIdCustomer = new Customer(UUID.randomUUID(), "wrongIdCustomer");
        assertThrows(DataModifyingException.class, () -> jdbcCustomerRepository.update(wrongIdCustomer));
    }

    @Test
    @DisplayName("Customer id를 통해 Customer를 삭제할 수 있다.")
    void deleteById() {
        //given
        jdbcCustomerRepository.save(customer);
        List<Customer> foundBeforeDelete = jdbcCustomerRepository.findAll();

        //when
        jdbcCustomerRepository.delete(customer.getId());

        //then
        List<Customer> foundAfterDelete = jdbcCustomerRepository.findAll();
        assertThat(foundAfterDelete.size()).isEqualTo(foundBeforeDelete.size() - 1);
    }

    @Test
    @DisplayName("조회할 수 없는 PK값을 갖고있는 Customer를 delete하려고 하면 실패한다.")
    void deleteFailIdNotFound() {
        //given
        jdbcCustomerRepository.save(customer);

        //when&then
        Customer wrongIdCustomer = new Customer(UUID.randomUUID(), "wrongIdCustomer");
        assertThrows(DataModifyingException.class, () -> jdbcCustomerRepository.delete(wrongIdCustomer.getId()));
    }

    @Test
    @DisplayName("Customer name을 통해 Customer를 삭제할 수 있다.")
    void deleteByName() {
        //given
        jdbcCustomerRepository.save(customer);
        List<Customer> foundBeforeDelete = jdbcCustomerRepository.findAll();

        //when
        jdbcCustomerRepository.delete(customer.getName());

        //then
        List<Customer> foundAfterDelete = jdbcCustomerRepository.findAll();
        assertThat(foundAfterDelete.size()).isEqualTo(foundBeforeDelete.size() - 1);
    }

    @Test
    @DisplayName("존재하지 않는 name을 통해 Customer를 delete하려고 하면 실패한다.")
    void deleteFailNameNotFound() {
        //given
        jdbcCustomerRepository.save(customer);

        //when&then
        String realName = customer.getName();
        customer.update("wrongName");
        jdbcCustomerRepository.update(customer);
        assertThrows(DataModifyingException.class, () -> jdbcCustomerRepository.delete(realName));
    }
}