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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import javax.sql.DataSource;

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

@ActiveProfiles("test")
@SpringJUnitConfig()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    Customer newCustomer, nameCopyNewCustomer, emailCopyNewCustomer, otherCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {

        newCustomer = new SimpleCustomer(UUID.randomUUID(), "test-customer", "test-customer@gmail.com", LocalDateTime.now());
        nameCopyNewCustomer = new SimpleCustomer(UUID.randomUUID(), "test-customer", "test-customer1@gmail.com", LocalDateTime.now());
        emailCopyNewCustomer = new SimpleCustomer(UUID.randomUUID(), "test-customer1", "test-customer@gmail.com", LocalDateTime.now());
        otherCustomer = new SimpleCustomer(UUID.randomUUID(), "other-customer", "other-customer@gmail.com", LocalDateTime.now());


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

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("customer insert 테스트")
    void testInsert() {
        try {
            customerRepository.insert(newCustomer);
            customerRepository.insert(otherCustomer);
        } catch (BadSqlGrammarException e) {
            logger.error("testInsert - BadSqlGrammarException -> {}",e.getSQLException().getErrorCode());
        }

        System.out.println("new Customer : => " + newCustomer.getCustomerId());

        Optional<Customer> retrieveCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrieveCustomer.isEmpty(), is(false));
        assertThat(retrieveCustomer.get(), samePropertyValuesAs(newCustomer));

    }

    @Test
    @Order(3)
    @Disabled
    @DisplayName("email이 같은 customer insert 테스트")
    void testInsertDuplicateByEmail() {
        try {
            customerRepository.insert(emailCopyNewCustomer);
        } catch (BadSqlGrammarException e) {
            logger.error("testInsert - BadSqlGrammarException -> {}",e.getSQLException().getErrorCode());
        }

        Optional<Customer> retrieveCustomer = customerRepository.findById(emailCopyNewCustomer.getCustomerId());
        assertThat(retrieveCustomer.isEmpty(), is(false));
        assertThat(retrieveCustomer.get(), samePropertyValuesAs(emailCopyNewCustomer));

    }
    @Test
    @Order(4)
    @DisplayName("이름이 같은 customer insert 테스트")
    void testInsertDuplicateByName() {
        try {
            customerRepository.insert(nameCopyNewCustomer);
        } catch (BadSqlGrammarException e) {
            logger.error("testInsert - BadSqlGrammarException -> {}",e.getSQLException().getErrorCode());
        } catch (DuplicateKeyException duplicateKeyException) {
            logger.error("testInsertByName - DuplicateKeyException -> {}", duplicateKeyException);
        }

        System.out.println("new Customer : => " + nameCopyNewCustomer.getCustomerId());

        Optional<Customer> retrieveCustomer = customerRepository.findById(nameCopyNewCustomer.getCustomerId());
        assertThat(retrieveCustomer.isEmpty(), is(false));
        assertThat(retrieveCustomer.get(), samePropertyValuesAs(nameCopyNewCustomer));

    }

    @Test
    @Order(5)
    @DisplayName("JdbcCustomerRepository 에 들어있는 customer 수 조회 테스트")
    void testCountJdbcCustomerRepository() {

        assertThat(customerRepository.count() == 3, is(true));
        System.out.println(customerRepository.count());
    }


    @Test
    @Order(5)
    @DisplayName("customer 전체 조회 테스트")
    void testFindAll() {
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(e -> System.out.println(MessageFormat.format("Email : {0}, name : {1}", e.getEmail(), e.getName())));
        assertThat(customers.isEmpty(), is(false));

    }

    @Test
    @Order(5)
    @DisplayName("customer Id 를 이용해 조회 테스트")
    void testFindById() {
        UUID customerId = newCustomer.getCustomerId();
        Optional<Customer> customer = customerRepository.findById(customerId);
        assertThat(customer.get(), samePropertyValuesAs(newCustomer));
    }


    @Test
    @Order(6)
    @DisplayName("저장되어 있는 customer 정보 수정 테스트")
    void testUpdateCustomer() {
        newCustomer.changeName("updated-customer");
        customerRepository.update(newCustomer);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers, hasSize(3));

        Optional<Customer> retrievedCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(),is(false));
        assertThat(retrievedCustomer.get(),samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(7)
    @DisplayName("customer 삭제 테스트")
    void testDeleteCustomer() {
        customerRepository.delete(newCustomer);
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers, hasSize(2));

    }




}