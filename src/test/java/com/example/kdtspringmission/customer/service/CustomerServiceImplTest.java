package com.example.kdtspringmission.customer.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.repository.CustomerRepository;
import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.service.VoucherService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CustomerServiceImplTest {


    @Test
    @DisplayName("모든 회원 정보의 리스트를 반환한다")
    void testAllCustomers() {

        // given
        List<Customer> customers = new ArrayList<>() {{
            add(new Customer(UUID.randomUUID(), "tester1", "tester1@test.com",
                LocalDateTime.now()));
            add(new Customer(UUID.randomUUID(), "tester2", "tester2@test.com",
                LocalDateTime.now()));
            add(new Customer(UUID.randomUUID(), "tester3", "tester3@test.com",
                LocalDateTime.now()));
        }};
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        when(customerRepository.findAll()).thenReturn(customers);
        CustomerService customerService = new CustomerService(customerRepository, voucherService);

        // when
        List<Customer> allCustomers = customerService.allCustomers();

        // then
        assertThat(allCustomers).hasSameElementsAs(customers);
        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("고객에게 바우처를 할당한다")
    void testAssignVoucher() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "tester", "tester@test.com",
            LocalDateTime.now());

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 150L);

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);

        when(customerRepository.findByName(customer.getName())).thenReturn(Optional.of(customer));
        when(voucherService.getVoucher(voucher.getId())).thenReturn(voucher);
        CustomerService customerService = new CustomerService(customerRepository, voucherService);

        //when
        customerService.assignVoucher(customer.getName(), voucher.getId());

        //then
        assertThat(voucher.getOwnerId()).isEqualTo(customer.getCustomerId());
        verify(customerRepository).findByName(customer.getName());
        verify(voucherService).getVoucher(voucher.getId());
    }

}
