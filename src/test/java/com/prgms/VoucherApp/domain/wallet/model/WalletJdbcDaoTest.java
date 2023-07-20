package com.prgms.VoucherApp.domain.wallet.model;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerDao;
import com.prgms.VoucherApp.domain.customer.model.CustomerJdbcDao;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.domain.voucher.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JdbcTest
@Import({VoucherJdbcDao.class, CustomerJdbcDao.class, WalletJdbcDao.class})
class WalletJdbcDaoTest {

    @Autowired
    private VoucherDao voucherDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private WalletDao walletDao;

    @Test
    @DisplayName("고객이 가진 할인권을 지갑에서 관리하도록 생성한다.")
    void saveWalletTest() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(5000));
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        voucherDao.save(voucher);
        customerDao.save(customer);
        Wallet wallet = new Wallet(UUID.randomUUID(), customer, voucher);

        // when
        walletDao.save(wallet);
        Optional<Wallet> findWalletId = walletDao.findById(wallet.getWalletId());

        // then
        Assertions.assertThat(findWalletId.get()).usingRecursiveComparison().isEqualTo(wallet);
    }

    @Test
    @DisplayName("고객이 가진 할인권을 전부 조회한다.")
    void findAllByCustomerIdTest() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(5000));
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        voucherDao.save(voucher);
        customerDao.save(customer);

        Voucher addedVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50));
        voucherDao.save(addedVoucher);

        Wallet walletA = new Wallet(UUID.randomUUID(), customer, voucher);
        Wallet walletB = new Wallet(UUID.randomUUID(), customer, addedVoucher);

        walletDao.save(walletA);
        walletDao.save(walletB);

        // when
        List<Wallet> findWallets = walletDao.findByCustomerId(customer.getCustomerId());

        // then
        Assertions.assertThat(findWallets)
                .extracting(Wallet::getVoucher)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(voucher, addedVoucher);
    }

    @Test
    @DisplayName("지갑에서 할인권 아이디로 조회 할 수 있다.")
    void findByVoucherId() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(5000));
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        voucherDao.save(voucher);
        customerDao.save(customer);

        Wallet wallet = new Wallet(UUID.randomUUID(), customer, voucher);
        walletDao.save(wallet);

        // when
        Optional<Wallet> findWallet = walletDao.findByVoucherId(voucher.getVoucherId());

        // then
        Assertions.assertThat(findWallet.get())
                .extracting(Wallet::getVoucher)
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("지갑을 삭제 할 수 있다.")
    void deleteByIdTest() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(5000));
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        voucherDao.save(voucher);
        customerDao.save(customer);

        Wallet wallet = new Wallet(UUID.randomUUID(), customer, voucher);
        walletDao.save(wallet);

        // when
        walletDao.deleteById(wallet.getWalletId());
        Optional<Wallet> findWallet = walletDao.findById(UUID.randomUUID());

        // then
        Assertions.assertThat(findWallet).isEmpty();
    }
}
