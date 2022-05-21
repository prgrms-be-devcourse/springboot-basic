package com.voucher.vouchermanagement.domain.customer.repository;

import com.voucher.vouchermanagement.domain.customer.repository.CustomerRepository;
import com.voucher.vouchermanagement.domain.customer.model.Customer;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @ComponentScan(basePackages = {"com.voucher.vouchermanagement.domain.customer.repository"})
    @AutoConfigureJdbc
    static class AppConfig {
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }
    }

    @BeforeAll
    public void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @AfterEach
    public void cleanEach() {
        customerRepository.deleteAll();
    }

    @AfterAll
    public void stopDb() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("Customer 데이터를 삽입한다.")
    public void insertTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "jh", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now());

        //when
        Customer insertedCustomer = customerRepository.insert(customer);

        //then
        assertThat(insertedCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("Id로 Customer를 식별한다.")
    public void findByIdTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "jh", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        //when
        Optional<Customer> insertedCustomer = customerRepository.findById(customer.getId());

        //then
        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("Email로 Customer를 식별한다.")
    public void findByEmailTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "jh", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        //when
        Optional<Customer> insertedCustomer = customerRepository.findByEmail(customer.getEmail());

        //then
        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("Name으로 Customer를 식별한다.")
    public void findByNameTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "jh", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        //when
        Optional<Customer> insertedCustomer = customerRepository.findByName(customer.getName());

        //then
        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("Customer 정보를 수정한다.")
    public void updateTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "jh", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        //when
        customer.login();
        customerRepository.update(customer);
        Optional<Customer> foundCustomer = customerRepository.findByEmail("pjh_jn@naver.com");

        //then
        assertThat(foundCustomer.isPresent(), is(true));
        assertThat(foundCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("다수의 Customer를 조회한다.")
    public void findAllTest() {
        //given
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "customerA", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "customerB", "mail@mail.com", LocalDateTime.now(), LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "customerC", "naver1@naver.com", LocalDateTime.now(), LocalDateTime.now())
        );
        customers.forEach(customerRepository::insert);

        //when
        List<Customer> foundCustomers = customerRepository.findAll();

        //then
        assertThat(foundCustomers.size(), is(3));
        assertThat(foundCustomers, containsInAnyOrder(samePropertyValuesAs(customers.get(0)),
                samePropertyValuesAs(customers.get(1)),
                samePropertyValuesAs(customers.get(2))
        ));
    }

    @Test
    @DisplayName("모든 Customer를 제거한다.")
    public void deleteAll() {
        //given
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "customerA", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "customerB", "mail@mail.com", LocalDateTime.now(), LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "customerC", "naver1@naver.com", LocalDateTime.now(), LocalDateTime.now())
        );
        customers.forEach(customerRepository::insert);

        //when
        customerRepository.deleteAll();
        List<Customer> foundCustomers = customerRepository.findAll();

        //then
        assertThat(foundCustomers.size(), is(0));
    }

    @Test
    @DisplayName("이메일은 중복될 수 없다.")
    public void duplicatedEmailTest() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customerB = new Customer(UUID.randomUUID(), "customerB", "pjh_jn@naver.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customerA);

        //when, then
        DuplicateKeyException duplicateKeyException = assertThrows(DuplicateKeyException.class, () -> customerRepository.insert(customerB));
    }
}
