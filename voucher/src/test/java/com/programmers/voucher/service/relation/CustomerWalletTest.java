package com.programmers.voucher.service.relation;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.customer.CustomerRepository;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import com.programmers.voucher.service.customer.BasicCustomerService;
import com.programmers.voucher.service.customer.CustomerVoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class CustomerWalletTest {

    CustomerRepository customerRepository = mock(CustomerRepository.class);
    VoucherRepository voucherRepository = mock(VoucherRepository.class);
    CustomerVoucherService customerVoucherService = new BasicCustomerService(customerRepository, voucherRepository);

    @Test
    @DisplayName("Get customer owning specific voucher")
    void getCustomerByVoucher() {
        Customer customer1 = new Customer(1L, "username1", "alias", false, LocalDate.now());
        Customer customer2 = new Customer(2L, "username2", "alias", false, LocalDate.now());
        Customer customer3 = new Customer(3L, "username3", "alias", false, LocalDate.now());

        long voucherId1 = 1111;
        long voucherId2 = 2222;
        long voucherId3 = 3333;

        when(customerRepository.findByVoucher(voucherId1))
                .thenReturn(Optional.of(customer1));
        when(customerRepository.findByVoucher(voucherId2))
                .thenReturn(Optional.of(customer2));
        when(customerRepository.findByVoucher(voucherId3))
                .thenReturn(Optional.of(customer3));

        final Optional<Customer> customerByVoucher1 = customerVoucherService.findCustomerByVoucher(voucherId1);
        verify(customerRepository).findByVoucher(voucherId1);
        assertTrue(customerByVoucher1.isPresent());
        assertEquals(customer1.getId(), customerByVoucher1.get().getId());

        final Optional<Customer> customerByVoucher2 = customerVoucherService.findCustomerByVoucher(voucherId2);
        verify(customerRepository).findByVoucher(voucherId2);
        assertTrue(customerByVoucher2.isPresent());
        assertEquals(customer2.getId(), customerByVoucher2.get().getId());

        final Optional<Customer> customerByVoucher3 = customerVoucherService.findCustomerByVoucher(voucherId3);
        verify(customerRepository).findByVoucher(voucherId3);
        assertTrue(customerByVoucher3.isPresent());
        assertEquals(customer3.getId(), customerByVoucher3.get().getId());
    }

    @Test
    @DisplayName("Get vouchers of customer")
    void getVouchers() {
        List<Voucher> list = new ArrayList<>(5);
        for (long i = 0; i < 5; i++) {
            list.add(new Voucher(i + 1, "voucherName" + i, new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1000 + i));
        }
        long customerId = 1;
        when(voucherRepository.findAllByCustomer(customerId))
                .thenReturn(list);

        final List<Voucher> allVoucherByCustomer = customerVoucherService.findAllVoucherByCustomer(customerId);
        verify(voucherRepository).findAllByCustomer(customerId);
        assertFalse(allVoucherByCustomer.isEmpty());
        for (int i = 0; i < 5; i++) {
            assertEquals(list.get(i), allVoucherByCustomer.get(i));
        }
    }
}
