package com.tangerine.voucher_system.application.customer.controller;

import com.tangerine.voucher_system.application.customer.controller.api.CustomerRestController;
import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.CustomerService;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
class CustomerRestControllerTest {

    @InjectMocks
    CustomerRestController controller;

    @Mock
    CustomerService service;

    @BeforeEach
    void init() {
        service = mock(CustomerService.class);
        controller = new CustomerRestController(service);
    }

    @Test
    @DisplayName("진상고객 정보를 리스트로 반환하면 성공한다.")
    void blackCustomerList_ParamVoid_ReturnCustomerResponseList() {
        given(service.findBlackCustomers()).willReturn(blackCustomerResults);

        ResponseEntity<List<CustomerResponse>> result = controller.blackCustomerList();

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody()).isNotEmpty();
        assertThat(result.getBody().get(0).customerId()).isSameAs(blackCustomerResults.get(0).customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 추가하면 성공한다.")
    @MethodSource("provideCreateCustomerRequests")
    void registerCustomer_ParamCreateCustomerRequests_InsertAndReturnCustomerResponse(CreateCustomerRequest request, CustomerResult result) {
        given(service.createCustomer(any())).willReturn(result);

        ResponseEntity<CustomerResponse> insertedCustomer = controller.registerCustomer(request);

        assertThat(insertedCustomer.getBody()).isNotNull();
        assertThat(insertedCustomer.getBody().name()).isEqualTo(result.name());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 업데이트하면 성공한다.")
    @MethodSource("provideUpdateCustomerRequests")
    void updateCustomer_ParamExistCustomerDto_UpdateCustomerDto(UpdateCustomerRequest request, CustomerResult result) {
        given(service.updateCustomer(any())).willReturn(result);

        ResponseEntity<CustomerResponse> updatedCustomer = controller.updateCustomer(request);

        assertThat(updatedCustomer.getBody()).isNotNull();
        assertThat(updatedCustomer.getBody().customerId()).isSameAs(result.customerId());
    }

    @Test
    @DisplayName("모든 고객을 반환하면 성공한다.")
    void customerList_ParamVoid_ReturnCustomerDtoList() {
        given(service.findAllCustomers()).willReturn(customerResults);

        ResponseEntity<List<CustomerResponse>> list = controller.customerList();

        assertThat(list.getBody()).isNotEmpty();
        assertThat(list.getBody().get(0).customerId()).isEqualTo(customerResults.get(0).customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 성공한다.")
    @MethodSource("provideCustomerResults")
    void customerById_ParamExistCustomerDto_ReturnCustomerDto(CustomerResult result) {
        given(service.findCustomerById(any())).willReturn(result);

        ResponseEntity<CustomerResponse> foundCustomer = controller.customerById(result.customerId());

        assertThat(foundCustomer.getBody()).isNotNull();
        assertThat(foundCustomer.getBody().customerId()).isEqualTo(result.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 이름으로 조회 시 성공한다.")
    @MethodSource("provideCustomerResults")
    void customerByName_ParamExistCustomerDto_ReturnCustomerDto(CustomerResult result) {
        given(service.findCustomerByName(any())).willReturn(result);

        ResponseEntity<CustomerResponse> foundCustomer = controller.customerByName(result.name());

        assertThat(foundCustomer.getBody()).isNotNull();
        assertThat(foundCustomer.getBody().customerId()).isEqualTo(result.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 제거하면 성공한다.")
    @MethodSource("provideCustomerResults")
    void unregisterCustomerById_ParamExistCustomer_ReturnAndDeleteCustomer(CustomerResult result) {
        given(service.deleteCustomerById(any())).willReturn(result);

        ResponseEntity<CustomerResponse> deletedCustomer = controller.unregisterCustomerById(result.customerId());

        assertThat(deletedCustomer.getBody()).isNotNull();
        assertThat(deletedCustomer.getBody().customerId()).isEqualTo(result.customerId());
    }

    static Stream<Arguments> provideCreateCustomerRequests() {
        return IntStream.range(0, customerResults.size())
                .mapToObj(i -> Arguments.of(createCustomerRequests.get(i), customerResults.get(i)));
    }

    static Stream<Arguments> provideUpdateCustomerRequests() {
        return IntStream.range(0, customerResults.size())
                .mapToObj(i -> Arguments.of(updateCustomerRequests.get(i), customerResults.get(i)));
    }

    static Stream<Arguments> provideCustomerResults() {
        return customerResults.stream()
                .map(Arguments::of);
    }

    static List<CustomerResult> blackCustomerResults = List.of(
            new CustomerResult(UUID.randomUUID(), new Name("상한사과"), true),
            new CustomerResult(UUID.randomUUID(), new Name("맛없는딸기"), true)
    );

    static List<CustomerResult> customerResults = List.of(
            new CustomerResult(UUID.randomUUID(), new Name("사과"), false),
            new CustomerResult(UUID.randomUUID(), new Name("딸기"), true),
            new CustomerResult(UUID.randomUUID(), new Name("포도"), false),
            new CustomerResult(UUID.randomUUID(), new Name("배"), false)
    );

    static List<CreateCustomerRequest> createCustomerRequests = customerResults.stream()
            .map(result -> new CreateCustomerRequest(result.name(), result.isBlack()))
            .toList();

    static List<UpdateCustomerRequest> updateCustomerRequests = customerResults.stream()
            .map(result -> new UpdateCustomerRequest(result.customerId(), result.name(), result.isBlack()))
            .toList();

}