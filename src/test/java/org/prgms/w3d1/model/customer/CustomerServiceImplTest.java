package org.prgms.w3d1.model.customer;

import org.junit.jupiter.api.Test;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.repository.CustomerRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.prgms.w3d1.service.CustomerServiceImpl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CustomerServiceImplTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
    private final CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, voucherRepository);

    /*
        given : voucher id를 만들어
        when : 메서드가 실행되면
        then : voucherRepository.findById와 customerRepository.findById가 동작되는지 확인한다.
     */

    @Test
    void testFindCustomerByVoucherId() {
        var voucherId = UUID.randomUUID();
        var customerId = UUID.randomUUID();
        var customer = new Customer(customerId, "test", "test@gmail.com", LocalDateTime.now());
        when(voucherRepository.findById(voucherId))
            .thenReturn(Optional.of(new FixedAmountVoucher(voucherId, 100L, customerId)));
        when(customerServiceImpl.findCustomerByVoucherId(voucherId))
            .thenReturn(Optional.of(customer));

        var testCustomer = customerServiceImpl.findCustomerByVoucherId(voucherId);

        assertThat(Objects.equals(testCustomer.get(), customer), is(true));
        verify(voucherRepository, times(2)).findById(voucherId);
        verify(customerRepository).findById(customerId);

    }
}