package com.devcourse.springbootbasic.application.customer.service;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.repository.CustomerRepository;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.BDDMockito.given;

@SpringJUnitConfig
class CustomerServiceTest {

    CustomerService sut;

    @Mock
    CustomerRepository customerRepositoryMock;

    @BeforeEach
    void cleanup() {
        sut = new CustomerService(customerRepositoryMock);
    }

    @Test
    @DisplayName("블랙고객 리스트 반환 시 성공한다.")
    void getBlackCustomers_VoidParam_ReturnVoucherList() {
        given(customerRepositoryMock.findAllBlackCustomers()).willReturn(customers);

        List<Customer> blackCustomers = sut.getBlackCustomers();

        assertThat(blackCustomers).isNotNull();
        assertThat(blackCustomers).isNotEmpty();
        assertThat(blackCustomers).isInstanceOf(List.class);
        assertThat(blackCustomers.get(0)).isInstanceOf(Customer.class);
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객 정보 추가 시 성공한다.")
    @MethodSource("provideValidCustomers")
    void registerCustomer_ParamValidCustomer_InsertAndReturnCustomer(Customer customer) {
        given(customerRepositoryMock.insert(customer)).willReturn(customer);

        Customer registeredCustomer = sut.registCustomer(customer);

        assertThat(registeredCustomer).isSameAs(customer);
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객 정보 아이디로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerById_ParamValidCustomer_ReturnCustomer(Customer customer) {
        given(customerRepositoryMock.findById(customer.getCustomerId())).willReturn(Optional.of(customer));

        Customer foundCustomer = sut.findCustomerById(customer.getCustomerId());

        assertThat(foundCustomer).isSameAs(customer);
    }

    @ParameterizedTest
    @DisplayName("정상적인 고객 정보 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerByName_ParamValidCustomer_ReturnCustomer(Customer customer) {
        given(customerRepositoryMock.findByName(customer.getName())).willReturn(Optional.of(customer));

        Customer foundCustomer = sut.findCustomerByName(customer.getName());

        assertThat(foundCustomer).isSameAs(customer);
    }

    @ParameterizedTest
    @DisplayName("비정상적인 고객 정보 아이디로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerById_ParamNotExistCustomer_Exception(Customer customer) {
        given(customerRepositoryMock.findById(customer.getCustomerId())).willReturn(Optional.empty());

        Exception exception = catchException(() -> sut.findCustomerById(customer.getCustomerId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("비정상적인 고객 정보 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void findCustomerByName_ParamNotExistCustomer_Exception(Customer customer) {
        given(customerRepositoryMock.findByName(customer.getName())).willReturn(Optional.empty());

        Exception exception = catchException(() -> sut.findCustomerByName(customer.getName()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 삭제하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void deleteCustomerById_ParamValidCustomer_ReturnCustomer(Customer customer) {
        given(customerRepositoryMock.findById(customer.getCustomerId())).willReturn(Optional.of(customer));

        Customer deletedCustomer = sut.deleteCustomerById(customer.getCustomerId());

        assertThat(deletedCustomer).isSameAs(customer);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 아이디로 삭제하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void deleteCustomerById_ParamNotExistCustomer_Exception(Customer customer) {
        given(customerRepositoryMock.findById(customer.getCustomerId())).willReturn(Optional.empty());

        Exception exception = catchException(() -> sut.deleteCustomerById(customer.getCustomerId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true),
            new Customer(UUID.randomUUID(), "포도", false),
            new Customer(UUID.randomUUID(), "배", false)
    );

    static Stream<Arguments> provideValidCustomers() {
        return customers.stream()
                .map(Arguments::of);
    }

}