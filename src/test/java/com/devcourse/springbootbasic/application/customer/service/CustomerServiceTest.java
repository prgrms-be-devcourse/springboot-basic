package com.devcourse.springbootbasic.application.customer.service;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.repository.CustomerRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    static List<Customer> validCustomers = List.of(
            new Customer(UUID.randomUUID(), "사과"),
            new Customer(UUID.randomUUID(), "딸기"),
            new Customer(UUID.randomUUID(), "포도"),
            new Customer(UUID.randomUUID(), "배")
    );
    @Autowired
    CustomerService customerService;

    EmbeddedMysql embeddedMysql;

    static Stream<Arguments> provideValidCustomers() {
        return validCustomers.stream()
                .map(Arguments::of);
    }

    @BeforeAll
    void init() {
        var mysqlConfig = aMysqldConfig(v8_0_17)
                .withCharset(UTF8)
                .withPort(8070)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_system", classPathScript("test-schema.sql"))
                .start();
    }

    @BeforeEach
    void cleanup() {
        customerService.deleteAllCustomers();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("블랙고객 리스트 반환 시 성공한다.")
    void GetBlackCustomers_VoidParam_ReturnVoucherList() {
        var customerRepositoryMock = mock(CustomerRepository.class);
        given(customerRepositoryMock.findAllBlackCustomers()).willReturn(
                List.of(
                        new Customer(UUID.fromString("061d89ad-1a6a-11ee-aed4-0242ac110002"), "사과"),
                        new Customer(UUID.fromString("06201b27-1a6a-11ee-aed4-0242ac110002"), "딸기"),
                        new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110002"), "포도"),
                        new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110003"), "배")
                )
        );
        var sut = new CustomerService(customerRepositoryMock);
        var blackCustomers = sut.getBlackCustomers();
        assertThat(blackCustomers, notNullValue());
        assertThat(blackCustomers, not(empty()));
        assertThat(blackCustomers, instanceOf(List.class));
        assertThat(blackCustomers.get(0), instanceOf(Customer.class));
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객 정보 추가 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void registerCustomer_ParamValidCustomer_InsertAndReturnCustomer(Customer customer) {
        customerService.registCustomer(customer);
        var registeredCustomer = customerService.findCustomerById(customer.getCustomerId());
        assertThat(registeredCustomer, samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객 정보 아이디로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerById_ParamValidCustomer_ReturnCustomer(Customer customer) {
        customerService.registCustomer(customer);
        var findedCustomer = customerService.findCustomerById(customer.getCustomerId());
        assertThat(findedCustomer, samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객 정보 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerByName_ParamValidCustomer_ReturnCustomer(Customer customer) {
        customerService.registCustomer(customer);
        var result = customerService.findCustomerByName(customer.getName());
        assertThat(result, samePropertyValuesAs(customer));
    }

    @ParameterizedTest
    @DisplayName("비정상적인 고객 정보 아이디로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerById_ParamNotExistCustomer_Exception(Customer customer) {
        Assertions.assertThrows(InvalidDataException.class,
                () -> customerService.findCustomerById(customer.getCustomerId()));
    }

    @ParameterizedTest
    @DisplayName("비정상적인 고객 정보 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerByName_ParamNotExistCustomer_Exception(Customer customer) {
        Assertions.assertThrows(InvalidDataException.class,
                () -> customerService.findCustomerByName(customer.getName()));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 삭제하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void deleteCustomerById_ParamValidCustomer_ReturnCustomer(Customer customer) {
        customerService.registCustomer(customer);
        var findedCustomer = customerService.deleteCustomerById(customer.getCustomerId());
        var allCustomers = customerService.findAllCustomers();
        assertThat(findedCustomer, samePropertyValuesAs(customer));
        assertThat(allCustomers.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("부재인 고객을 아이디로 삭제하면 실패한다.")
    @MethodSource("provideValidCustomers")
    void deleteCustomerById_ParamNotExistCustomer_Exception(Customer customer) {
        Assertions.assertThrows(InvalidDataException.class,
                () -> customerService.findCustomerById(customer.getCustomerId()));
    }

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

}