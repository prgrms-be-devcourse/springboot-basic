package org.prgms.kdtspringweek1.wallet.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.customer.repository.JdbcCustomerRepository;
import org.prgms.kdtspringweek1.exception.DataException;
import org.prgms.kdtspringweek1.exception.DataExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.repository.JdbcVoucherRepository;
import org.prgms.kdtspringweek1.wallet.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class JdbcWalletRepositoryTest {

    @Autowired
    private JdbcWalletRepository jdbcWalletRepository;

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.update("DELETE FROM wallets", Collections.emptyMap());
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Test
    @DisplayName("지갑 저장 성공")
    void Success_Save() {
        // given
        Voucher voucher = saveVoucher();
        Customer customer = saveCustomer();
        Wallet wallet = Wallet.createWithVoucherIdAndCustomerId(voucher.getVoucherId(), customer.getCustomerId());

        // when
        Wallet savedWallet = jdbcWalletRepository.save(wallet);

        // then
        assertThat(savedWallet, samePropertyValuesAs(wallet));
    }

    @Test
    @DisplayName("특정 고객이 가진 바우처 조회 성공")
    void Success_FindAllVouchersByCustomerId() {
        // given
        List<Voucher> vouchers = Stream.generate(this::saveVoucher).limit(3).toList();
        Customer customer = saveCustomer();
        for (Voucher voucher : vouchers) {
            jdbcWalletRepository.save(Wallet.createWithVoucherIdAndCustomerId(voucher.getVoucherId(), customer.getCustomerId()));
        }

        // when
        List<Voucher> allVouchersByCustomerId = jdbcWalletRepository.findAllVouchersByCustomerId(customer.getCustomerId());

        // then
        assertThat(allVouchersByCustomerId, hasSize(vouchers.size()));
    }

    @Test
    @DisplayName("특정 고객이 가진 특정 바우처에 해당하는 지갑 삭제 성공")
    void Success_DeleteByVoucherIdAndCustomerId() {
        // given
        Voucher voucher = saveVoucher();
        Customer customer = saveCustomer();
        jdbcWalletRepository.save(Wallet.createWithVoucherIdAndCustomerId(voucher.getVoucherId(), customer.getCustomerId()));
        int beforeDelete = jdbcWalletRepository.findAllCustomersByVoucherId(voucher.getVoucherId()).size();

        // when
        jdbcWalletRepository.deleteByVoucherIdAndCustomerId(voucher.getVoucherId(), customer.getCustomerId());

        // then
        assertThat(jdbcWalletRepository.findAllCustomersByVoucherId(voucher.getVoucherId()), hasSize(beforeDelete - 1));
    }

    @Test
    @DisplayName("특정 고객이 가진 특정 바우처에 해당하는 지갑 삭제 실패 - 고객이 존재하지 않는 경우")
    void Fail_DeleteByVoucherIdAndCustomerId_NotExistingVoucher() {
        // given
        Voucher voucher = saveVoucher();
        Customer customer = saveCustomer();

        // when
        DataException exception = assertThrows(DataException.class, () -> {
            jdbcWalletRepository.deleteByVoucherIdAndCustomerId(voucher.getVoucherId(), customer.getCustomerId());
        });

        // then
        assertThat(exception.getMessage(), is(DataExceptionCode.FAIL_TO_DELETE.getMessage()));
    }

    @Test
    @DisplayName("특정 바우처를 가진 고객 조회 성공")
    void Success_FindAllCustomersByVoucherId() {
        // given
        Voucher voucher = saveVoucher();
        List<Customer> customers = Stream.generate(this::saveCustomer).limit(3).toList();
        for (Customer customer : customers) {
            jdbcWalletRepository.save(Wallet.createWithVoucherIdAndCustomerId(voucher.getVoucherId(), customer.getCustomerId()));
        }

        // when
        List<Customer> allCustomersByVoucherId = jdbcWalletRepository.findAllCustomersByVoucherId(voucher.getVoucherId());

        // then
        assertThat(allCustomersByVoucherId, hasSize(customers.size()));
    }

    private Customer saveCustomer() {
        return jdbcCustomerRepository.save(Customer.createWithNameAndIsBlackCustomer("최정은", new Random().nextBoolean()));
    }

    private Voucher saveVoucher() {
        int voucherNum = new Random().nextInt(2);
        if (voucherNum == 0) {
            return jdbcVoucherRepository.save(FixedAmountVoucher.createWithAmount(300));
        } else {
            return jdbcVoucherRepository.save(PercentDiscountVoucher.createWithPercent(30));
        }
    }
}