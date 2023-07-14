package com.devcourse.springbootbasic.application.customer.repository;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_17;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    static List<Customer> validCustomers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true),
            new Customer(UUID.randomUUID(), "포도", false),
            new Customer(UUID.randomUUID(), "배", false)
    );
    @Autowired
    CustomerJdbcRepository customerRepository;
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
        customerRepository.deleteAll();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("블랙고객 리스트를 반환하면 성공한다.")
    void findAllBlackCustomers_Normal_ReturnBlackCustomers() {
        validCustomers.forEach(customer -> customerRepository.insert(customer));

        List<Customer> result = customerRepository.findAllBlackCustomers();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).isBlack()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객으로 추가 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void insert_ParamNotExistCustomer_InsertAndReturnCustomer(Customer customer) {
        customerRepository.insert(customer);

        Optional<Customer> insertedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(insertedCustomer).isNotEmpty();
        assertThat(insertedCustomer.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객으로 추가하려고 했을때 실패한다.")
    @MethodSource("provideValidCustomers")
    void insert_ParamExistCustomer_Exception(Customer customer) {
        customerRepository.insert(customer);
        Exception exception = catchException(() -> customerRepository.insert(customer));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객 아이디로 업데이트할 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void update_ParamExistCustomerId_ReturnAndUpdateCustomer(Customer customer) {
        customerRepository.insert(customer);

        customerRepository.update(customer);

        Optional<Customer> updatedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(updatedCustomer.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 아이디로 업데이트할 시 실패한다.")
    @MethodSource("provideValidCustomers")
    void update_ParamNotExistCustomerId_ReturnAndUpdateCustomer(Customer customer) {
        Exception exception = catchException(() -> customerRepository.update(customer));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("전체 고객 리스트 반환 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findAll_ParamVoid_ReturnVoucherList(Customer customer) {
        customerRepository.insert(customer);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findById_ParamExistCustomerId_ReturnCustomerOptional(Customer customer) {
        customerRepository.insert(customer);

        Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());

        assertThat(foundCustomer.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 아이디로 조회 시 실패한다.")
    @MethodSource("provideValidCustomers")
    void findById_ParamNotExistCustomerId_ReturnEmptyOptional(Customer customer) {
        Optional<Customer> result = customerRepository.findById(customer.getCustomerId());

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객 이름으로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findByName_ParamExistName_ReturnCustomerOptional(Customer customer) {
        customerRepository.insert(customer);

        Optional<Customer> foundCustomer = customerRepository.findByName(customer.getName());

        assertThat(foundCustomer.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 이름으로 조회 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void findByName_ParamNotExistName_ReturnEmptyOptional(Customer customer) {
        customerRepository.insert(customer);
        Optional<Customer> result = customerRepository.findByName(customer.getName());

        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("모든 데이터가 삭제되면 성공한다.")
    void deleteAll_ParamVoid_DeleteAllCustomers() {
        customerRepository.deleteAll();

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 고객을 아이디로 삭제 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void deleteById_ParamExistCustomer_ReturnAndDeleteCustomer(Customer customer) {
        customerRepository.insert(customer);

        customerRepository.deleteById(customer.getCustomerId());
        Optional<Customer> result = customerRepository.findById(customer.getCustomerId());

        assertThat(result).isEmpty();
    }

}