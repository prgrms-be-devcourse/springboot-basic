package org.prgrms.voucherapp.engine.customer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.customer.repository.CustomerRepository;
import org.prgrms.voucherapp.engine.wallet.repository.WalletRepository;
import org.prgrms.voucherapp.exception.NullCustomerException;
import org.prgrms.voucherapp.global.enums.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

// TODO : 객체가 stubbing의 parameter로 들어갈 때 Argument Mismatch 발생하는 문제.
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    WalletRepository walletRepository;

    private final Customer customer = new Customer(UUID.randomUUID(), "Kim Seung Eun", "duliesoures@gmail.com");
    private final Customer customerWithStatus = new Customer(UUID.randomUUID(), "Kim Seung Su", "rlatmdtn@gmail.com", CustomerStatus.VIP.toString());

    @Test
    @DisplayName("존재하는 고객을 조회한다.")
    void testGetExistCustomer() {
        //Given
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        //When
        Customer customer = customerService.getCustomer(this.customer.getCustomerId());

        //Then
        assertThat(customer, samePropertyValuesAs(this.customer));
        verify(customerRepository, atLeast(1)).findById(this.customer.getCustomerId());
    }

    @Test
    @DisplayName("존재하지 않는 고객을 조회한다.")
    void testGetNonExistCustomer() {
        //Given
        Customer unknown = new Customer(UUID.randomUUID(), "unknown", "unknown@gmail.com");
        //When
        assertThrows(NullCustomerException.class, () -> customerService.getCustomer(unknown.getCustomerId()));
        verify(customerRepository, atLeast(1)).findById(unknown.getCustomerId());
    }

    @Test
    @DisplayName("상태가 없는 고객을 생성한다.")
    void createNormalCustomer() {
        //Given
        when(customerRepository.insert(any())).thenReturn(customer);
        Optional<CustomerStatus> customerStatus= CustomerStatus.getStatus(customer.getStatus());
        //When
        Customer result = customerService.createCustomer(customer.getCustomerId(), customer.getName(), customer.getEmail(), customerStatus);
        //Then
        assertThat(customerStatus, is(Optional.empty()));
        assertThat(result, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("상태가 있는 고객을 생성한다.")
    void createCustomerWithStatus() {
        //Given
        when(customerRepository.insert(any())).thenReturn(customerWithStatus);
        Optional<CustomerStatus> customerStatus= CustomerStatus.getStatus(customerWithStatus.getStatus());
        //When
        Customer result = customerService.createCustomer(customerWithStatus.getCustomerId(), customerWithStatus.getName(), customerWithStatus.getEmail(), customerStatus);
        //Then
        customerStatus.ifPresent(status -> assertThat(status, is(CustomerStatus.VIP)));
        assertThat(result, samePropertyValuesAs(customerWithStatus));
    }

    @Test
    @DisplayName("존재하는 고객 제거")
    void removeCustomer() {
        customerService.removeCustomer(customer);
        verify(customerRepository).deleteById(customer.getCustomerId());
        verify(walletRepository).deleteByCustomerId(customer.getCustomerId());
    }

    @Test
    @DisplayName("상태가 없는 고객에게 상태 부여")
    void giveCustomerStatus(){
        CustomerStatus expectedStatus = CustomerStatus.VIP;
        Customer expected = new Customer(customer.getCustomerId(), customer.getName(), customer.getEmail(), expectedStatus.toString());
        when(customerRepository.update(customer)).thenReturn(customer);
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(expected));

        customerService.updateCustomer(customer, expected.getName(), Optional.of(expectedStatus));
        Customer result = customerService.getCustomer(customer.getCustomerId());

        assertThat(result, samePropertyValuesAs(expected));
        verify(customerRepository).update(customer);
    }

    @Test
    @DisplayName("상태가 있는 고객의 상태 제거")
    void removeCustomerStatus(){
        Customer expected = new Customer(customerWithStatus.getCustomerId(), customerWithStatus.getName(),  customerWithStatus.getEmail());
        when(customerRepository.update(customerWithStatus)).thenReturn(customerWithStatus);
        when(customerRepository.findById(customerWithStatus.getCustomerId())).thenReturn(Optional.of(expected));

        customerService.updateCustomer(customerWithStatus, expected.getName(), Optional.empty());
        Customer result = customerService.getCustomer(customerWithStatus.getCustomerId());

        assertThat(result, samePropertyValuesAs(expected));
        verify(customerRepository).update(customerWithStatus);
    }
}