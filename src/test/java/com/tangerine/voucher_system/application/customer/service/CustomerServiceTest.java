package com.tangerine.voucher_system.application.customer.service;

import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.repository.JdbcCustomerRepository;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@JdbcTest
@ActiveProfiles("test")
@Import({CustomerService.class, JdbcCustomerRepository.class})
class CustomerServiceTest {

    @Autowired
    CustomerService service;

    @Test
    @DisplayName("블랙고객 리스트 반환 시 성공한다.")
    void findBlackCustomers_ParamVoid_ReturnVoucherList() {
        customerParams.forEach(service::createCustomer);

        List<CustomerResult> blackCustomers = service.findBlackCustomers();

        assertThat(blackCustomers).isNotEmpty();
        assertThat(blackCustomers.get(0).isBlack()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객 정보 추가 시 성공한다.")
    @MethodSource("provideCustomerParams")
    void createCustomer_ParamNotExistCustomer_InsertAndReturnCustomer(CustomerParam param) {

        service.createCustomer(param);

        CustomerResult registeredCustomer = service.findCustomerById(param.customerId());
        assertThat(registeredCustomer.customerId()).isEqualTo(param.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객 정보 추가 시 실패한다.")
    @MethodSource("provideCustomerParams")
    void createCustomer_ParamExistCustomer_Exception(CustomerParam param) {
        service.createCustomer(param);

        Exception exception = catchException(() -> service.createCustomer(param));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 업데이트 시 성공한다.")
    @MethodSource("provideCustomerParams")
    void updateCustomer_ParamExistCustomer_UpdateAndReturnCustomer(CustomerParam param) {
        service.createCustomer(param);

        CustomerParam newCustomer = new CustomerParam(param.customerId(), new Name("new_name"), true);
        service.updateCustomer(newCustomer);

        CustomerResult updatedCustomer = service.findCustomerById(param.customerId());
        assertThat(updatedCustomer.customerId()).isEqualTo(newCustomer.customerId());
        assertThat(updatedCustomer.customerId()).isEqualTo(param.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 업데이트 시 실패한다.")
    @MethodSource("provideCustomerParams")
    void updateCustomer_ParamNotExistCustomer_Exception(CustomerParam param) {

        CustomerParam newCustomer = new CustomerParam(param.customerId(), new Name("new_name"), true);
        Exception exception = catchException(() -> service.updateCustomer(newCustomer));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @Test
    @DisplayName("모든 고객을 리스트로 반환한다.")
    void findAllCustomers_ParamVoid_ReturnCustomerList() {
        customerParams.forEach(service::createCustomer);

        List<CustomerResult> customers = service.findAllCustomers();

        assertThat(customers).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideCustomerParams")
    void findCustomerById_ParamExistCustomer_ReturnCustomer(CustomerParam param) {
        service.createCustomer(param);

        CustomerResult foundCustomer = service.findCustomerById(param.customerId());

        assertThat(foundCustomer.customerId()).isEqualTo(param.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 아이디로 조회 시 해당 고객 반환하면 실패한다.")
    @MethodSource("provideCustomerParams")
    void findCustomerById_ParamNotExistCustomer_Exception(CustomerParam param) {

        Exception exception = catchException(() -> service.findCustomerById(param.customerId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideCustomerParams")
    void findCustomerByName_ParamExistCustomer_ReturnCustomer(CustomerParam param) {
        service.createCustomer(param);

        CustomerResult foundCustomer = service.findCustomerByName(param.name());

        assertThat(foundCustomer.customerId()).isEqualTo(param.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 이름으로 조회 시 해당 고객 반환하면 성공한다.")
    @MethodSource("provideCustomerParams")
    void findCustomerByName_ParamNotExistCustomer_Exception(CustomerParam param) {

        Exception exception = catchException(() -> service.findCustomerByName(param.name()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 삭제하면 성공한다.")
    @MethodSource("provideCustomerParams")
    void deleteCustomerById_ParamValidCustomer_ReturnCustomer(CustomerParam param) {
        service.createCustomer(param);

        CustomerResult deletedCustomer = service.deleteCustomerById(param.customerId());

        assertThat(deletedCustomer.customerId()).isEqualTo(param.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 아이디로 삭제하면 성공한다.")
    @MethodSource("provideCustomerParams")
    void deleteCustomerById_ParamNotExistCustomer_Exception(CustomerParam param) {

        Exception exception = catchException(() -> service.deleteCustomerById(param.customerId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    static Stream<Arguments> provideCustomerParams() {
        return customerParams.stream()
                .map(Arguments::of);
    }

    static List<CustomerParam> customerParams = List.of(
            new CustomerParam(UUID.randomUUID(), new Name("사과"), false),
            new CustomerParam(UUID.randomUUID(), new Name("딸기"), true),
            new CustomerParam(UUID.randomUUID(), new Name("포도"), false),
            new CustomerParam(UUID.randomUUID(), new Name("배"), false)
    );

}