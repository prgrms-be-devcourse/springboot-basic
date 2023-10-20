package org.prgrms.vouchermanager.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.prgrms.vouchermanager.Repository.MemoryCustomerRepository;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @Mock
    private MemoryCustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    @DisplayName("save함수가 성공적으로 호출되어야 한다")
    void createOrder() {
        Customer customer = new Customer(String.valueOf(UUID.randomUUID()), "Injun");

        when(repository.save(any())).thenReturn(customer);

        Customer newCustomer = service.createCustomer("Injun");
        assertThat(customer).isEqualTo(newCustomer);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("findall함수가 성공적으로 호출되어야 한다")
    void findAll(){
        List<Customer> all = service.findAll();
        verify(repository).findAll();
    }
}