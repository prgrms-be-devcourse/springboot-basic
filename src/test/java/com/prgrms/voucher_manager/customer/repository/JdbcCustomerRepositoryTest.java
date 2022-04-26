package com.prgrms.voucher_manager.customer.repository;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.SimpleCustomer;
import com.wix.mysql.EmbeddedMysql;
//import com.prgrms.voucher_manager.customer.JdbcCustomer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

@SpringJUnitConfig()
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"com.prgrms.voucher_manager.customer.repository"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-jdbc-customer-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer, newCustomer2;

    EmbeddedMysql embeddedMysql;

    @BeforeEach
    void setup() {

        newCustomer = new SimpleCustomer(UUID.randomUUID(), "test-customer", "test-customer@gmail.com", LocalDateTime.now());
        newCustomer2 = new SimpleCustomer(UUID.randomUUID(), "other-customer", "other-customer@gmail.com", LocalDateTime.now());


        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-jdbc-customer-mgmt", classPathScript("customerSchemaTest.sql"))
                .start();
    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("customer insert 테스트")
    void testInsert() {

        customerRepository.insert(newCustomer);
        customerRepository.insert(newCustomer2);

        Optional<Customer> retrieveNewCustomer = customerRepository.findById(newCustomer.getCustomerId());
        Optional<Customer> retrieveOtherCustomer = customerRepository.findById(newCustomer2.getCustomerId());
        assertThat(retrieveNewCustomer.isEmpty(), is(false));
        assertThat(retrieveNewCustomer.get(), samePropertyValuesAs(newCustomer));
        assertThat(retrieveOtherCustomer.get(), samePropertyValuesAs(newCustomer2));
    }

    @Test
    @DisplayName("email이 같은 customer insert 테스트")
    void testInsertDuplicateByEmail() {

        customerRepository.insert(newCustomer);

        SimpleCustomer testCustomer = new SimpleCustomer(UUID.randomUUID(), "beomsic", newCustomer.getEmail(), LocalDateTime.now());

        try{
            customerRepository.insert(testCustomer);
        } catch (DataAccessException e) {};

        Optional<Customer> retrieveCustomer = customerRepository.findById(testCustomer.getCustomerId());
        assertThat(retrieveCustomer.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이름이 같은 customer insert 테스트")
    void testInsertDuplicateByName() {

        customerRepository.insert(newCustomer);

        SimpleCustomer testCustomer = new SimpleCustomer(UUID.randomUUID(), newCustomer.getName(), "beomsic@gmail.com", LocalDateTime.now());
        customerRepository.insert(testCustomer);

        Optional<Customer> retrieveCustomer = customerRepository.findById(testCustomer.getCustomerId());
        assertThat(retrieveCustomer.isEmpty(), is(false));
        assertThat(retrieveCustomer.get(), samePropertyValuesAs(testCustomer));
    }

    @Test
    @DisplayName("customer 수 조회 테스트")
    void testCountJdbcCustomerRepository() {
        assertThat(customerRepository.count() == 0, is(true));
        customerRepository.insert(newCustomer);
        customerRepository.insert(newCustomer2);
        assertThat(customerRepository.count() == 2, is(true));
    }

    @Test
    @DisplayName("customer 전체 조회 테스트")
    void testFindAll() {
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
        customerRepository.insert(newCustomer);
        customerRepository.insert(newCustomer2);
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers, hasSize(2));
    }

    @Test
    @DisplayName("customer Id 를 이용해 조회 테스트")
    void testFindById() {
        customerRepository.insert(newCustomer);
        UUID customerId = newCustomer.getCustomerId();
        Optional<Customer> customer = customerRepository.findById(customerId);
        assertThat(customer.isPresent(), is(true));
        assertThat(customer.get(), samePropertyValuesAs(newCustomer));
    }


    @Test
    @DisplayName("저장되어 있는 customer 정보 수정 테스트")
    void testUpdateCustomer() {

        customerRepository.insert(newCustomer);
        customerRepository.insert(newCustomer2);

        newCustomer.changeName("updated-customer");
        customerRepository.update(newCustomer);

        Optional<Customer> retrievedCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(),is(false));
        assertThat(retrievedCustomer.get(),samePropertyValuesAs(newCustomer));

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers, hasSize(2));
    }

    @Test
    @DisplayName("customer 삭제 테스트")
    void testDeleteCustomer() {
        customerRepository.insert(newCustomer);
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers, hasSize(1));

        customerRepository.delete(newCustomer);
        customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }




}