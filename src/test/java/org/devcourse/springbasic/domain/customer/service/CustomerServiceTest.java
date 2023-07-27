package org.devcourse.springbasic.domain.customer.service;

import org.devcourse.springbasic.domain.customer.dao.CustomerRepository;
import org.devcourse.springbasic.domain.customer.domain.Customer;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        // Mock 객체 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("고객 추가")
    void save() {
        // given
        CustomerDto.SaveRequest saveRequest = new CustomerDto.SaveRequest("lee", "lee@example.com");
        // when
        when(customerRepository.save(any(Customer.class))).thenReturn(UUID.randomUUID());
        UUID generatedUUID = customerService.save(saveRequest);
        // then
        assertThat(generatedUUID).isNotNull();
    }

    @Test
    @DisplayName("고객이름 수정")
    void update() {
        // given
        Customer customer = new Customer(
                UUID.randomUUID(),
                "lee",
                "lee@example.com",
                null,
                LocalDateTime.now()
        );

        // when
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(java.util.Optional.of(customer));
        when(customerRepository.update(any(Customer.class))).thenReturn(customer.getCustomerId());
        CustomerDto.UpdateRequest updateRequest =
                new CustomerDto.UpdateRequest(customer.getCustomerId(), "new Lee");
        UUID updatedCustomerId = customerService.update(updateRequest);

        // Then
        assertThat(updatedCustomerId).isEqualTo(customer.getCustomerId());
        assertThat(customer.getName()).isEqualTo("new Lee");
    }

    @Test
    @DisplayName("없는 고객 수정 시도 -> 예외발생")
    void update_NonCustomer() {
        // given
        UUID nonCustomerId = UUID.randomUUID();
        when(customerRepository.findById(nonCustomerId)).thenReturn(java.util.Optional.empty());
        CustomerDto.UpdateRequest updateRequest = new CustomerDto.UpdateRequest(nonCustomerId, "newName");

        // when
        assertThatThrownBy(() -> customerService.update(updateRequest))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("회원을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("id로 고객 삭제")
    void deleteById() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId,
                "lee",
                "lee@example.com",
                null,
                LocalDateTime.now()
        );
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        // when
        customerService.deleteById(customerId);
        // then
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    @DisplayName("이름으로 고객 조회")
    void findByCriteriaByName() {
        // given
        Customer customerLee = new Customer(UUID.randomUUID(), "lee", "lee@example.com", null, LocalDateTime.now());
        Customer customerKim = new Customer(UUID.randomUUID(), "kim", "kim@example.com", null, LocalDateTime.now());
        when(customerRepository.findByName("lee")).thenReturn(List.of(customerLee));
        CustomerDto.Request request = new CustomerDto.Request("lee", null);

        // when
        List<CustomerDto.Response> matchingCustomers = customerService.findByCriteria(request);

        // then
        assertThat(matchingCustomers).hasSize(1);
        assertThat(matchingCustomers.get(0).getName()).isEqualTo("lee");
    }

    @Test
    @DisplayName("이메일로 고객 조회")
    void findByCriteriaByEmail() {
        // given
        UUID customerId = UUID.randomUUID();
        String customerEmail = "lee@example.com";
        Customer customer = new Customer(
                customerId,
                "lee",
                customerEmail,
                null,
                LocalDateTime.now()
        );
        when(customerRepository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        CustomerDto.Request request = new CustomerDto.Request(null, customerEmail);

        // when
        List<CustomerDto.Response> matchingCustomers = customerService.findByCriteria(request);

        // then
        assertThat(matchingCustomers).hasSize(1);
        assertThat(matchingCustomers.get(0).getEmail()).isEqualTo(customerEmail);
    }

    @Test
    @DisplayName("아무 필터도 없으면 전체 고객을 조회")
    void findByCriteriaNothing() {
        // given
        Customer customerLee = new Customer(UUID.randomUUID(),  "lee","lee@example.com", null, LocalDateTime.now());
        Customer customerKim = new Customer(UUID.randomUUID(),  "kim","kim@example.com", null, LocalDateTime.now());
        when(customerRepository.findAll()).thenReturn(List.of(customerLee, customerKim));
        CustomerDto.Request request = new CustomerDto.Request(null, null);
        // When
        List<CustomerDto.Response> allCustomers = customerService.findByCriteria(request);
        // Then
        assertThat(allCustomers).hasSize(2);
    }
}
