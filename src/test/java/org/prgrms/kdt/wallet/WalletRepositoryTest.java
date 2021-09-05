package org.prgrms.kdt.wallet;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.BaseRepositoryTest;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 9:57 오후
 */
class WalletRepositoryTest extends BaseRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    WalletJdbcRepository walletJdbcRepository;

    @Test
    @DisplayName("바우처 지갑 추가 테스트")
    void addVoucherWallet() {
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        voucherRepository.insert(givenVoucher(voucherId));
        customerRepository.insert(givenCustomer(customerId));

        Customer customer = customerRepository.findById(customerId).get();
        Voucher voucher = voucherRepository.findById(voucherId).get();

        walletJdbcRepository.insert(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));
    }

    private Customer givenCustomer(UUID customerId) {
        return new Customer(customerId, "tester", "tester@email.com", LocalDateTime.now());
    }

    private Voucher givenVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 100L, VoucherType.FIX, LocalDateTime.now());
    }
}