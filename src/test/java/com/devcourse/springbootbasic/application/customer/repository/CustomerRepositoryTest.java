package com.devcourse.springbootbasic.application.customer.repository;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_17;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRepositoryTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:8070/test-voucher_system")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
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

    @Autowired
    CustomerRepository customerRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void init() {
        customerRepository.setFilePath("storage/customers/customer_blacklist.csv");

        var mysqlConfig = aMysqldConfig(v8_0_17)
                .withCharset(UTF8)
                .withPort(8070)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_system", classPathScript("test-customer_schema.sql"))
                .start();
    }

    @BeforeEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("블랙고객 리스트를 반환하면 성공한다.")
    void FindAllBlackCustomers_Normal_ReturnBlackCustomers() {
        var result = customerRepository.findAllBlackCustomers();
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Customer.class));
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객으로 추가 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void insert_ParamValidCustomer_InsertAndReturnCustomer(Customer customer) {
        customerRepository.insert(customer);
        var insertedCustomer = customerRepository.findById(customer.getCustomerId());

        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), instanceOf(Customer.class));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객으로 추가하려고 했을때 실패한다.")
    @MethodSource("provideInvalidCustomers")
    void insert_ParamInvalidCustomer_Exception(Customer customer) {
        customerRepository.insert(customer);
        Assertions.assertThrows(InvalidDataException.class, () -> customerRepository.insert(customer));
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객 아이디로 업데이트할 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void update_ParamExistCustomerId_ReturnAndUpdateCustomer(Customer customer) {
        customerRepository.insert(customer);
        customerRepository.update(customer);
        var updatedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(updatedCustomer.isPresent(), is(true));
        assertThat(updatedCustomer.get(), instanceOf(Customer.class));
        assertThat(updatedCustomer.get(), samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 아이디로 업데이트할 시 실패한다.")
    @MethodSource("provideValidCustomers")
    void update_ParamNotExistCustomerId_ReturnAndUpdateCustomer(Customer customer) {
        Assertions.assertThrows(InvalidDataException.class, () -> customerRepository.update(customer));
    }

    @ParameterizedTest
    @DisplayName("전체 고객 리스트 반환 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findAll_ParamVoid_ReturnVoucherList(Customer customer) {
        customerRepository.insert(customer);
        var customers = customerRepository.findAll();
        var customersCount = customerRepository.count();
        assertThat(customers, instanceOf(List.class));
        assertThat(customers.get(0), instanceOf(Customer.class));
        assertThat(customers.size(), is(customersCount));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findById_ParamExistCustomerId_ReturnCustomerOptional(Customer customer) {
        customerRepository.insert(customer);
        var findedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(findedCustomer.isPresent(), is(true));
        assertThat(findedCustomer.get(), instanceOf(Customer.class));
        assertThat(findedCustomer.get(), samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 아이디로 조회 시 실패한다.")
    @MethodSource("provideValidCustomers")
    void findById_ParamNotExistCustomerId_ReturnEmptyOptional(Customer customer) {
        var result = customerRepository.findById(customer.getCustomerId());
        assertThat(result.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객 이름으로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findByName_ParamExistName_ReturnCustomerOptional(Customer customer) {
        customerRepository.insert(customer);
        var findedCustomer = customerRepository.findByName(customer.getName());
        assertThat(findedCustomer.isPresent(), is(true));
        assertThat(findedCustomer.get(), instanceOf(Customer.class));
        assertThat(findedCustomer.get(), samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 이름으로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findByName_ParamNotExistName_ReturnEmptyOptional(Customer customer) {
        var result = customerRepository.findByName(customer.getName());
        assertThat(result.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객 이메일로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findByName_ParamExistEmail_ReturnCustomerOptional(Customer customer) {
        customerRepository.insert(customer);
        var findedCustomer = customerRepository.findByEmail(customer.getEmail());
        assertThat(findedCustomer.isPresent(), is(true));
        assertThat(findedCustomer.get(), instanceOf(Customer.class));
        assertThat(findedCustomer.get(), samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 이메일로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findByName_ParamNotExistEmail_ReturnEmptyOptional(Customer customer) {
        var result = customerRepository.findByEmail(customer.getEmail());
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    @DisplayName("모든 데이터가 삭제되면 성공한다.")
    void deleteAll_ParamVoid_DeleteAllCustomers() {
        customerRepository.deleteAll();
        var customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객을 아이디로 삭제 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void deleteById_ParamExistCustomer_ReturnAndDeleteCustomer(Customer customer) {
        customerRepository.insert(customer);
        var beforeCount = customerRepository.count();
        customerRepository.deleteById(customer.getCustomerId());
        var afterCount = customerRepository.count();
        assertThat(beforeCount, is(afterCount + 1));
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객을 이름으로 삭제 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void deleteByName_ParamExistCustomer_ReturnAndDeleteCustomer(Customer customer) {
        customerRepository.insert(customer);
        var beforeCount = customerRepository.count();
        customerRepository.deleteByName(customer.getName());
        var afterCount = customerRepository.count();
        assertThat(beforeCount, is(afterCount + 1));
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객을 이메일로 삭제 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void deleteByEmail_ParamExistCustomer_ReturnAndDeleteCustomer(Customer customer) {
        customerRepository.insert(customer);
        var beforeCount = customerRepository.count();
        customerRepository.deleteByEmail(customer.getEmail());
        var afterCount = customerRepository.count();
        assertThat(beforeCount, is(afterCount + 1));
    }

    static Stream<Arguments> provideValidCustomers() {
        return validCustomers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideInvalidCustomers() {
        return validCustomers.stream()
                .map(Arguments::of);
    }

    static List<Customer> validCustomers = List.of(
            new Customer(UUID.randomUUID(), "사과", "apple@naver.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "딸기", "strawberry@naver.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "포도", "grape@naver.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "배", "peach@naver.com", LocalDateTime.now())
    );

    static List<Customer> invalidCustomers = List.of(
            new Customer(UUID.randomUUID(), "사과", "fix@naver.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "딸기", "fix@naver.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "포도", "fix@naver.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "배", "fix@naver.com", LocalDateTime.now())
    );

}