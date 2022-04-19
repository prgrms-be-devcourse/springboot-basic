package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
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
import org.prgms.voucherProgram.domain.customer.Email;
import org.prgms.voucherProgram.dto.CustomerRequest;
import org.prgms.voucherProgram.repository.customer.BlackListRepository;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private BlackListRepository fileCustomerRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @DisplayName("저장시 이메일이 중복되지 않은 고객이라면 고객을 저장한다.")
    @Test
    void Should_ReturnCustomer_When_saveCustomer() {
        // given
        CustomerRequest customerRequest = customerRequest();
        Customer customer = customer();
        given(customerRepository.save(any(Customer.class))).willReturn(customer);
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // when
        Customer saveCustomer = customerService.save(customerRequest);

        //then
        assertThat(saveCustomer).usingRecursiveComparison().isEqualTo(customer);
        then(customerRepository).should(times(1)).save(any(Customer.class));
        then(customerRepository).should(times(1)).findByEmail(anyString());
    }

    @DisplayName("저장시 이메일이 중복되는 고객이라면 예외가 발생한다.")
    @Test
    void Should_ThrowException_When_saveDuplicateCustomer() {
        // given
        CustomerRequest customerRequest = customerRequest();
        Customer customer = customer();
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));

        // when
        assertThatThrownBy(() -> customerService.save(customerRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이미 해당 이메일로 저장된 고객이 있습니다.");
        then(customerRepository).should(times(1)).findByEmail(anyString());
        then(customerRepository).should(times(0)).save(any(Customer.class));
    }

    @DisplayName("이메일을 통해 고객을 찾는다.")
    @Test
    void Should_ReturnCustomerDto_When_CustomerIsExists() {
        //given
        Customer customer = customer();
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));

        //when
        Customer findCustomer = customerService.findByEmail(new Email(customer.getEmail()));

        //then
        assertThat(findCustomer).usingRecursiveComparison()
            .isEqualTo(customer);
        then(customerRepository).should(times(1)).findByEmail(any(String.class));
    }

    @DisplayName("조회시 해당하는 이메일을 가진 고객이 없다면 예외를 발생한다.")
    @Test
    void Should_ThrowException_When_FindCustomerIsNotExists() {
        //given
        Email email = new Email("hwan@gmail.com");
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> customerService.findByEmail(email))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
        then(customerRepository).should(times(1)).findByEmail(any(String.class));
    }

    @DisplayName("고객을 수정한다.")
    @Test
    void Should_UpdateCustomer() {
        //given
        Email email = email();
        CustomerRequest customerRequest = new CustomerRequest("spancer", "spancer@gmail.com");
        Customer customer = customer();
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
        customer.changeInformation(customerRequest.getName(), customerRequest.getEmail(), LocalDateTime.now());
        given(customerRepository.update(any(Customer.class))).willReturn(customer);

        //when
        Customer updateCustomer = customerService.update(email, customerRequest);

        //then
        assertThat(updateCustomer).usingRecursiveComparison()
            .isEqualTo(customer);
        then(customerRepository).should(times(2)).findByEmail(any(String.class));
        then(customerRepository).should(times(1)).update(any(Customer.class));
    }

    @DisplayName("중복되는 이메일로 수정 시 예외를 발생한다.")
    @Test
    void Should_ThrowException_When_UpdateDuplicateEmail() {
        //given
        Email email = email();
        CustomerRequest customerRequest = new CustomerRequest("aramand", "aramand@gmail.com");
        Customer customer = customer();
        given(customerRepository.findByEmail(email.getEmail())).willReturn(Optional.of(customer));

        customer.changeInformation(customerRequest.getName(), customerRequest.getEmail(), LocalDateTime.now());
        Customer duplicateCustomer = new Customer(UUID.randomUUID(), "spancer", "hwan@gmail.com",
            LocalDateTime.now());
        given(customerRepository.findByEmail(customer.getEmail())).willReturn(Optional.of(duplicateCustomer));

        //when
        //then
        assertThatThrownBy(() -> customerService.update(email, customerRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이미 해당 이메일로 저장된 고객이 있습니다.");
        then(customerRepository).should(times(2)).findByEmail(any(String.class));
        then(customerRepository).should(times(0)).update(any(Customer.class));
    }

    @DisplayName("수정시 해당하는 이메일을 가진 고객이 없다면 예외를 발생한다.")
    @Test
    void Should_ThrowException_When_UpdateNotExistsCustomer() {
        //given
        Email email = email();
        CustomerRequest customerRequest = customerRequest();
        given(customerRepository.findByEmail(email.getEmail())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> customerService.update(email, customerRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
        then(customerRepository).should(times(1)).findByEmail(any(String.class));
        then(customerRepository).should(times(0)).update(any(Customer.class));

    }

    @DisplayName("고객을 삭제한다.")
    @Test
    void Should_deleteCustomer_When_CustomerIsExists() {
        // given
        Email email = email();
        Customer customer = customer();
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));

        // when
        customerService.delete(email);

        // then
        then(customerRepository).should(times(1)).findByEmail(customer.getEmail());
        then(customerRepository).should(times(1)).deleteByEmail(customer.getEmail());
    }

    @DisplayName("삭제시 해당하는 이메일을 가진 고객이 없다면 예외를 발생한다.")
    @Test
    void Should_ThrowException_When_DeleteCustomerIsNotExists() {
        // given
        Email email = email();
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> customerService.delete(email))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
        then(customerRepository).should(times(1)).findByEmail(any(String.class));
        then(customerRepository).should(times(0)).deleteByEmail(any(String.class));
    }

    @DisplayName("모든 고객을 반환한다.")
    @Test
    void Should_ReturnCustomers() {
        //given
        List<Customer> customers = customers();
        given(customerRepository.findAll()).willReturn(customers);

        //when
        List<Customer> findCustomers = customerService.findCustomers();

        //then
        assertThat(findCustomers).hasSize(2)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .containsAll(findCustomers);
        then(customerRepository).should(times(1)).findAll();
    }

    @DisplayName("모든 블랙리스트를 반환한다.")
    @Test
    void findBlackList_ReturnBlackCustomers() {
        // given
        List<Customer> mockBlackCustomers = customers();
        given(fileCustomerRepository.findBlackCustomers()).willReturn(mockBlackCustomers);

        //when
        List<Customer> blackList = customerService.findBlackList();

        //then
        assertThat(blackList).hasSize(2)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .containsAll(mockBlackCustomers);
        then(fileCustomerRepository).should(times(1)).findBlackCustomers();
    }

    private Customer customer() {
        return new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
    }

    private List<Customer> customers() {
        return List.of(new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "pobi", "pobi@gmail.com", LocalDateTime.now()));
    }

    private CustomerRequest customerRequest() {
        return new CustomerRequest("hwan", "hwan@gmail.com");
    }

    private Email email() {
        return new Email("hwan@gmail.com");
    }
}
