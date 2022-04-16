package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.repository.customer.BlackListRepository;
import org.prgms.voucherProgram.repository.customer.JdbcCustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private BlackListRepository fileCustomerRepository;

    @Mock
    private JdbcCustomerRepository jdbcCustomerRepository;

    @InjectMocks
    private CustomerService customerService;

    @DisplayName("이메일이 중복되지 않은 고객이라면 고객을 저장한다.")
    @Test
    void Should_ReturnCustomer_When_NonDuplicateEmail() {
        // given
        CustomerDto customerDto = new CustomerDto(UUID.randomUUID(), "hwan", "hwan@gmail.com");
        given(jdbcCustomerRepository.save(any(Customer.class))).willReturn(customerDto.toEntity());
        given(jdbcCustomerRepository.findByEmail(anyString())).willReturn(Optional.empty());
        // when
        CustomerDto newCustomer = customerService.save(customerDto);
        //then
        assertThat(newCustomer).usingRecursiveComparison().isEqualTo(customerDto);
        then(jdbcCustomerRepository).should(times(1)).save(any(Customer.class));
        then(jdbcCustomerRepository).should(times(1)).findByEmail(anyString());
    }

    @DisplayName("이메일이 중복되는 고객이라면 예외가 발생한다.")
    @Test
    void Should_ThrowException_When_DuplicateEmail() {
        // given
        CustomerDto customerDto = new CustomerDto(UUID.randomUUID(), "hwan", "hwan@gmail.com");
        given(jdbcCustomerRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(customerDto.toEntity()));
        // when
        assertThatThrownBy(() -> customerService.save(customerDto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이미 존재하는 고객입니다.");
        then(jdbcCustomerRepository).should(times(1)).findByEmail(anyString());
        then(jdbcCustomerRepository).should(times(0)).save(any(Customer.class));
    }

    @DisplayName("이메일을 통해 고객을 찾는다.")
    @Test
    void Should_ReturnCustomerDto_When_CustomerIsExists() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        given(jdbcCustomerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
        //when
        CustomerDto customerDto = customerService.findByEmail(customer.getEmail());
        //then
        assertThat(customerDto).usingRecursiveComparison().isEqualTo(CustomerDto.from(customer));
        then(jdbcCustomerRepository).should(times(1)).findByEmail(any(String.class));
    }

    @DisplayName("해당하는 이메일을 가진 고객이 없다면 예외를 발생한다.")
    @Test
    void Should_ThrowException_When_CustomerIsNotExists() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        given(jdbcCustomerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> customerService.findByEmail(customer.getEmail()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR} 해당 이메일로 저장된 고객이 없습니다.");
        then(jdbcCustomerRepository).should(times(1)).findByEmail(any(String.class));
    }

    @DisplayName("모든 블랙리스트를 반환한다.")
    @Test
    void findBlackList_ReturnBlackCustomers() {
        List<Customer> mockBlackCustomers = Arrays.asList(new Customer(UUID.randomUUID(), "jin", "jin@gmail.com", null,
                null),
            new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", null, null),
            new Customer(UUID.randomUUID(), "pobi", "pobi@gmail.com", null, null));

        given(fileCustomerRepository.findBlackCustomers()).willReturn(mockBlackCustomers);

        assertThat(customerService.findBlackList()).hasSize(3)
            .extracting("name", "email").contains(tuple("hwan", "hwan@gmail.com"),
                tuple("pobi", "pobi@gmail.com"),
                tuple("jin", "jin@gmail.com"));
        then(fileCustomerRepository).should(times(1)).findBlackCustomers();
    }
}
