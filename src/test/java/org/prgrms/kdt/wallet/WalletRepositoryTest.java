package org.prgrms.kdt.wallet;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.EmbeddedMysqlConnector;
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
class WalletRepositoryTest extends EmbeddedMysqlConnector {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    WalletJdbcRepository walletJdbcRepository;

    Customer customer = givenCustomer(UUID.randomUUID());
    Voucher voucher = givenVoucher(UUID.randomUUID());

    @BeforeEach
    void beforeEach() {
        walletJdbcRepository.deleteAll();
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
        customerRepository.insert(customer);
        voucherRepository.insert(voucher);
    }

    @Test
    @DisplayName("바우처 지갑 추가 테스트")
    void addVoucherWallet() {
        int insert = walletJdbcRepository.insert(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));

        assertThat(insert).isEqualTo(1);
    }

    @Test
    @DisplayName("고객의 아이디로 바우처 조회 테스트")
    void findByCustomerId() {
        walletJdbcRepository.insert(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));
        List<Voucher> vouchers = voucherRepository.findVouchersByCustomerId(customer.getCustomerId());

        assertThat(vouchers.size()).isEqualTo(1);
        assertThat(vouchers).contains(voucher);
    }

    @Test
    @DisplayName("바우처 아이디로 고객 조회 테스트")
    void findByVoucherId() {
        walletJdbcRepository.insert(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));
        List<Customer> customers = customerRepository.findCustomersByVoucherId(voucher.getVoucherId());

        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers).contains(customer);
    }

    @Test
    @DisplayName("고객에게 등록된 바우처 삭제 테스트")
    void deleteByWallet() {
        walletJdbcRepository.deleteBy(customer.getCustomerId(), voucher.getVoucherId());
        List<Voucher> vouchers = voucherRepository.findVouchersByCustomerId(customer.getCustomerId());
        List<Customer> customers = customerRepository.findCustomersByVoucherId(voucher.getVoucherId());

        assertThat(vouchers).isEmpty();
        assertThat(customers).isEmpty();
    }

    private Customer givenCustomer(UUID customerId) {
        return new Customer(customerId, "tester", "tester@email.com", LocalDateTime.now());
    }

    private Voucher givenVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 100L, VoucherType.FIX, LocalDateTime.now());
    }
}