package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.service.CustomerService;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
class CustomerControllerTest {

    static List<Customer> validCustomers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true),
            new Customer(UUID.randomUUID(), "포도", false),
            new Customer(UUID.randomUUID(), "배", false)
    );
    static List<CustomerDto> validCustomerDto = List.of(
            new CustomerDto(UUID.randomUUID(), "사과", false),
            new CustomerDto(UUID.randomUUID(), "딸기", true),
            new CustomerDto(UUID.randomUUID(), "포도", false),
            new CustomerDto(UUID.randomUUID(), "배", false)
    );
    @Autowired
    CustomerController customerController;
    EmbeddedMysql embeddedMysql;

    static Stream<Arguments> provideValidCustomers() {
        return validCustomers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideValidCustomerDto() {
        return validCustomerDto.stream()
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
        customerController.deleteAllCustomers();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("진상고객 정보를 리스트로 반환하면 성공한다.")
    void findBlackCustomers_ParamVoid_ReturnCustomerDtoList() {
        var serviceMock = mock(CustomerService.class);
        given(serviceMock.findAllCustomers()).willReturn(validCustomers);
        var sut = new CustomerController(serviceMock);
        var result = sut.customerList();
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), instanceOf(CustomerDto.class));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 추가하면 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void registCustomer_ParamCustomer_InsertAndReturnCustomerDto(CustomerDto customerDto) {
        customerController.registerCustomer(customerDto);
        var insertedCustomer = customerController.findCustomerById(customerDto.customerId());
        assertThat(insertedCustomer, notNullValue());
        assertThat(insertedCustomer, instanceOf(CustomerDto.class));
        assertThat(insertedCustomer.name(), is(customerDto.name()));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 추가하면 실패한다.")
    @MethodSource("provideValidCustomers")
    void registCustomer_ParamNotExistCustomerDto_Exception(Customer customer) {
        var dto = CustomerDto.of(customer);
        customerController.registerCustomer(dto);
        Assertions.assertThrows(InvalidDataException.class, () -> customerController.registerCustomer(dto));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 업데이트하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void updateCustomer_ParamExistCustomerDto_UpdateCustomerDto(Customer customer) {
        var dto = CustomerDto.of(customer);
        customerController.registerCustomer(dto);
        var otherCustomer = new Customer(customer.getCustomerId(), "토끼", false);
        var otherDto = CustomerDto.of(otherCustomer);
        customerController.updateCustomer(otherDto);
        var updatedDto = customerController.findCustomerById(otherDto.customerId());
        assertThat(updatedDto.name(), is(otherDto.name()));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 업데이트하면 실패한다.")
    @MethodSource("provideValidCustomers")
    void updateCustomer_ParamNotExistCustomerDto_Exception(Customer customer) {
        var otherCustomer = new Customer(customer.getCustomerId(), "토끼", false);
        var otherDto = CustomerDto.of(otherCustomer);
        Assertions.assertThrows(InvalidDataException.class, () -> customerController.updateCustomer(otherDto));
    }

    @Test
    @DisplayName("모든 고객을 반환하면 성공한다.")
    void findAllCustomers_PararmVoid_ReturnCustomerDtoList() {
        var customerServiceMock = mock(CustomerService.class);
        given(customerServiceMock.findAllCustomers()).willReturn(validCustomers);
        var sut = new CustomerController(customerServiceMock);
        var list = sut.customerList();
        assertThat(list, notNullValue());
        assertThat(list.get(0), instanceOf(CustomerDto.class));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void findCustomerById_ParamExistCustomerDto_ReturnCustomerDto(CustomerDto customerDto) {
        customerController.registerCustomer(customerDto);
        var findedCustomerDto = customerController.findCustomerById(customerDto.customerId());
        assertThat(findedCustomerDto, samePropertyValuesAs(customerDto));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 아이디로 조회 시 실패한다.")
    @MethodSource("provideValidCustomerDto")
    void findCustomerById_ParamNotExistCustomerDto_Exception(CustomerDto customerDto) {
        Assertions.assertThrows(InvalidDataException.class, () -> customerController.findCustomerById(customerDto.customerId()));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 이름으로 조회 시 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void findCustomerByName_ParamExistCustomerDto_ReturnCustomerDto(CustomerDto customerDto) {
        customerController.registerCustomer(customerDto);
        var findedCustomerDto = customerController.findCustomerByName(customerDto.name());
        assertThat(findedCustomerDto, samePropertyValuesAs(customerDto));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 이름으로 조회 시 실패한다.")
    @MethodSource("provideValidCustomerDto")
    void findCustomerByName_ParamNotExistCustomerDto_Exception(CustomerDto customerDto) {
        Assertions.assertThrows(InvalidDataException.class, () -> customerController.findCustomerByName(customerDto.name()));
    }

    @Test
    @DisplayName("모든 고객을 제거하면 성공한다.")
    void deleteAllCustomers_ParamVoid_DeleteAllCustomers() {
        customerController.deleteAllCustomers();
        var allCustomers = customerController.customerList();
        assertThat(allCustomers.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 제거하면 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void deleteCustomerById_ParamExistCustomer_ReturnAndDeleteCustomer(CustomerDto customerDto) {
        customerController.registerCustomer(customerDto);
        var deletedCustomer = customerController.unregisterCustomerById(customerDto.customerId());
        assertThat(deletedCustomer, samePropertyValuesAs(customerDto));
        Assertions.assertThrows(InvalidDataException.class, () -> customerController.findCustomerById(customerDto.customerId()));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 아이디로 제거하면 실패한다.")
    @MethodSource("provideValidCustomerDto")
    void deletCustomerById_ParamNotExistCustomer_Exception(CustomerDto customerDto) {
        Assertions.assertThrows(InvalidDataException.class, () -> customerController.unregisterCustomerById(customerDto.customerId()));
    }

}