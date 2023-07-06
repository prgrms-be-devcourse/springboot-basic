package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.CustomerResponse;
import com.programmers.voucher.domain.customer.dto.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.entity.Customer;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import com.programmers.voucher.exception.ConflictException;
import com.programmers.voucher.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("고객 생성에 성공한다.")
    void 고객_생성_성공() {
        // given
        CustomerCreateRequest request = new CustomerCreateRequest("test");
        Customer customer = new Customer(UUID.randomUUID(), "test");
        given(customerRepository.insert(any(Customer.class))).willReturn(customer);

        // when
        CustomerResponse response = customerService.createCustomer(request);

        // then
        assertThat(response.customerId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("이미 존재하는 닉네임인 경우 예외가 발생한다.")
    void 고객_생성_예외() {
        // given
        CustomerCreateRequest request = new CustomerCreateRequest("test");
        Customer customer = new Customer(UUID.randomUUID(), "test");
        given(customerRepository.findByNickname("test")).willReturn(Optional.of(customer));

        // when & then
        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @Test
    @DisplayName("모든 고객 조회에 성공한다.")
    void 모든_고객_조회_성공() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1");
        Customer customer2 = new Customer(UUID.randomUUID(), "test2");
        List<Customer> customers = List.of(customer1, customer2);
        given(customerRepository.findAll()).willReturn(customers);

        // when
        List<CustomerResponse> response = customerService.getAllCustomers();

        // then
        assertThat(response).hasSize(2);
    }

    @Test
    @DisplayName("고객 조회에 성공한다.")
    void 고객_조회_성공() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test");
        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));

        // when
        CustomerResponse response = customerService.getCustomer(customer.getId());

        // then
        assertThat(response.nickname()).isEqualTo(customer.getNickname());
    }

    @Test
    @DisplayName("존재하지 않는 고객을 조회한 경우 예외가 발생한다.")
    void 고객_조회_예외() {
        // given
        UUID customerId = UUID.randomUUID();
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> customerService.getCustomer(customerId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하는 고객이 없습니다.");
    }

    @Test
    @DisplayName("고객 수정에 성공한다.")
    void 고객_수정_성공() {
        // given
        CustomerUpdateRequest request = new CustomerUpdateRequest("new");
        Customer customer = new Customer(UUID.randomUUID(), "test");
        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(customerRepository.update(customer)).willReturn(customer);

        // when
        CustomerResponse response = customerService.updateCustomer(customer.getId(), request);

        // then
        assertThat(response.nickname()).isEqualTo("new");
    }
}
