package com.tangerine.voucher_system.application.customer.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true),
            new Customer(UUID.randomUUID(), "포도", false),
            new Customer(UUID.randomUUID(), "배", false)
    );
    @Autowired
    CustomerService service;

    static Stream<Arguments> provideCustomers() {
        return customers.stream()
                .map(Arguments::of);
    }

    @BeforeEach
    void cleanup() {
        service.deleteAllCustomers();
    }

    @Test
    @DisplayName("블랙고객 리스트 반환 시 성공한다.")
    void findBlackCustomers_ParamVoid_ReturnVoucherList() {
        customers.forEach(service::createCustomer);

        List<Customer> blackCustomers = service.findBlackCustomers();

        assertThat(blackCustomers).isNotEmpty();
        assertThat(blackCustomers.get(0).isBlack()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 정보 추가 시 성공한다.")
    @MethodSource("provideCustomers")
    void createCustomer_ParamNotExistCustomer_InsertAndReturnCustomer(Customer customer) {

        service.createCustomer(customer);

        Customer registeredCustomer = service.findCustomerById(customer.getCustomerId());
        assertThat(registeredCustomer).isEqualTo(customer);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객 정보 추가 시 실패한다.")
    @MethodSource("provideCustomers")
    void createCustomer_ParamExistCustomer_Exception(Customer customer) {
        service.createCustomer(customer);

        Exception exception = catchException(() -> service.createCustomer(customer));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 업데이트 시 성공한다.")
    @MethodSource("provideCustomers")
    void updateCustomer_ParamExistCustomer_UpdateAndReturnCustomer(Customer customer) {
        service.createCustomer(customer);

        Customer newCustomer = new Customer(customer.getCustomerId(), "new_name", true);
        service.updateCustomer(newCustomer);

        Customer updatedCustomer = service.findCustomerById(customer.getCustomerId());
        assertThat(updatedCustomer).isEqualTo(newCustomer);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 업데이트 시 실패한다.")
    @MethodSource("provideCustomers")
    void updateCustomer_ParamNotExistCustomer_Exception(Customer customer) {

        Customer newCustomer = new Customer(customer.getCustomerId(), "new_name", true);
        Exception exception = catchException(() -> service.updateCustomer(newCustomer));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @Test
    @DisplayName("모든 고객을 리스트로 반환한다.")
    void findAllCustomers_ParamVoid_ReturnCustomerList() {
        customers.forEach(service::createCustomer);

        List<Customer> customers = service.findAllCustomers();

        assertThat(customers).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideCustomers")
    void findCustomerById_ParamExistCustomer_ReturnCustomer(Customer customer) {
        service.createCustomer(customer);

        Customer foundCustomer = service.findCustomerById(customer.getCustomerId());

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 아이디로 조회 시 해당 고객 반환하면 실패한다.")
    @MethodSource("provideCustomers")
    void findCustomerById_ParamNotExistCustomer_Exception(Customer customer) {

        Exception exception = catchException(() -> service.findCustomerById(customer.getCustomerId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideCustomers")
    void findCustomerByName_ParamExistCustomer_ReturnCustomer(Customer customer) {
        service.createCustomer(customer);

        Customer foundCustomer = service.findCustomerByName(customer.getName());

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideCustomers")
    void findCustomerByName_ParamNotExistCustomer_Exception(Customer customer) {

        Exception exception = catchException(() -> service.findCustomerByName(customer.getName()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 삭제하면 성공한다.")
    @MethodSource("provideCustomers")
    void deleteCustomerById_ParamValidCustomer_ReturnCustomer(Customer customer) {
        service.createCustomer(customer);

        Customer deletedCustomer = service.deleteCustomerById(customer.getCustomerId());

        assertThat(deletedCustomer).isEqualTo(customer);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 아이디로 삭제하면 성공한다.")
    @MethodSource("provideCustomers")
    void deleteCustomerById_ParamNotExistCustomer_Exception(Customer customer) {

        Exception exception = catchException(() -> service.deleteCustomerById(customer.getCustomerId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

}