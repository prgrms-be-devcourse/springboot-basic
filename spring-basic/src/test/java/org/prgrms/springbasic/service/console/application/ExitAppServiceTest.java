package org.prgrms.springbasic.service.console.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.voucher.FixedAmountVoucher;
import org.prgrms.springbasic.repository.customer.BlackCustomerRepository;
import org.prgrms.springbasic.repository.voucher.MemoryVoucherRepository;
import org.prgrms.springbasic.service.console.ConsoleService;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ExitAppServiceTest {

    MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
    BlackCustomerRepository customerRepository = new BlackCustomerRepository();
    ConsoleService service = new ExitAppService(voucherRepository, customerRepository);

    @Test
    @DisplayName("프로그램 종료 후 각 레포지토리에는 저장된 값이 없어야한다.")
    void testExitServiceTest() {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        var blackCustomer = Customer.blackCustomer(UUID.randomUUID(), "customer");

        voucherRepository.save(voucher);
        customerRepository.save(blackCustomer);

        assertThat(voucherRepository.countStorageSize(), is(1));
        assertThat(customerRepository.countStorageSize(), is(1));

        service.execute();

        assertThat(voucherRepository.countStorageSize(), is(0));
        assertThat(customerRepository.countStorageSize(), is(0));
    }
}