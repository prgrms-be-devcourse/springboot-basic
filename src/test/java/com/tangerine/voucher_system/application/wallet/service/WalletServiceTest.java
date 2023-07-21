package com.tangerine.voucher_system.application.wallet.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.repository.JdbcCustomerRepository;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.repository.JdbcVoucherRepository;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.repository.JdbcWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@JdbcTest
@ActiveProfiles("test")
@Import({WalletService.class, JdbcWalletRepository.class, JdbcCustomerRepository.class, JdbcVoucherRepository.class})
class WalletServiceTest {

    @Autowired
    WalletService service;

    @Autowired
    JdbcVoucherRepository voucherRepository;
    @Autowired
    JdbcCustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        vouchers.forEach(voucherRepository::insert);
        customers.forEach(customerRepository::insert);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 추가하면 성공한다.")
    @MethodSource("provideWallets")
    void insert_ParamNotExistWallet_InsertWallet(Wallet wallet) {

        service.createWallet(wallet);

        List<Wallet> result = service.findWalletsByCustomerId(wallet.customerId());
        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 추가하면 실패한다.")
    @MethodSource("provideWallets")
    void insert_ParamExistWallet_Exception(Wallet wallet) {
        service.createWallet(wallet);

        Exception exception = catchException(() -> service.createWallet(wallet));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 업데이트하면 성공한다.")
    @MethodSource("provideWallets")
    void update_ParamExistWallet_UpdateWallet(Wallet wallet) {
        service.createWallet(wallet);
        Voucher newVoucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 21), LocalDate.now());
        voucherRepository.insert(newVoucher);

        Wallet newWallet = new Wallet(wallet.walletId(), newVoucher.getVoucherId(), wallet.customerId());
        service.updateWallet(newWallet);

        List<Wallet> result = service.findWalletsByCustomerId(wallet.customerId());
        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 업데이트하면 실패한다.")
    @MethodSource("provideWallets")
    void update_ParamNotExistWallet_Exception(Wallet wallet) {

        Exception exception = catchException(() -> service.updateWallet(wallet));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 삭제하면 성공한다.")
    @MethodSource("provideWallets")
    void deleteById_ParamExistWalletId_DeleteWallet(Wallet wallet) {
        service.createWallet(wallet);

        service.deleteWalletById(wallet.walletId());

        boolean result = service.findWalletsByCustomerId(wallet.customerId())
                .stream()
                .anyMatch(w -> Objects.equals(wallet.walletId(), w.walletId()));
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 고객 아이디로 조회하면 성공한다.")
    @MethodSource("provideWallets")
    void findByCustomerId_ParamExistWallet_ReturnWallet(Wallet wallet) {
        service.createWallet(wallet);

        List<Wallet> result = service.findWalletsByCustomerId(wallet.customerId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 고객 아이디로 조회하면 실패한다.")
    @MethodSource("provideWallets")
    void findByCustomerId_ParamNotExistWallet_ReturnWallet(Wallet wallet) {

        List<Wallet> wallets = service.findWalletsByCustomerId(wallet.customerId());

        assertThat(wallets).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 바우처 아이디로 조회하면 성공한다.")
    @MethodSource("provideWallets")
    void findByVoucherId_ParamExistWallet_ReturnWallet(Wallet wallet) {
        service.createWallet(wallet);

        List<Wallet> result = service.findWalletsByVoucherId(wallet.voucherId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 바우처 아이디로 조회하면 실패한다.")
    @MethodSource("provideWallets")
    void findByVoucherId_ParamNotExistWallet_ReturnWallet(Wallet wallet) {

        List<Wallet> wallets = service.findWalletsByVoucherId(wallet.voucherId());

        assertThat(wallets).isEmpty();
    }

    @Test
    @DisplayName("바우처, 고객, 지갑이 존재할 때 고객 아이디로 조회 시 성공한다.")
    void findVouchersByCustomerId_ParamAllExist_ReturnVoucherList() {
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 2), LocalDate.now());
        Customer customer = new Customer(UUID.randomUUID(), new Name("사과"), true);
        voucherRepository.insert(voucher);
        customerRepository.insert(customer);
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher.getVoucherId(), customer.customerId());
        service.createWallet(wallet);

        List<Voucher> vouchers = service.findVouchersByCustomerId(wallet.customerId());

        assertThat(vouchers).isNotEmpty();
    }

    @Test
    @DisplayName("바우처, 고객, 지갑이 존재할 때 바우처 아이디로 조회 시 성공한다.")
    void findCustomersByVoucherId_ParamAllExist_ReturnCustomerList() {
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 2), LocalDate.now());
        Customer customer = new Customer(UUID.randomUUID(), new Name("사과"), true);
        voucherRepository.insert(voucher);
        customerRepository.insert(customer);
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher.getVoucherId(), customer.customerId());
        service.createWallet(wallet);

        List<Customer> vouchers = service.findCustomersByVoucherId(wallet.voucherId());

        assertThat(vouchers).isNotEmpty();
    }

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), LocalDate.now()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 10), LocalDate.now()),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 41), LocalDate.now())
    );

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), new Name("사과"), false),
            new Customer(UUID.randomUUID(), new Name("딸기"), true),
            new Customer(UUID.randomUUID(), new Name("배"), false)
    );

    static List<Wallet> wallets = IntStream.range(0, vouchers.size())
            .mapToObj(i -> new Wallet(UUID.randomUUID(), vouchers.get(i).getVoucherId(), customers.get(0).customerId()))
            .toList();

    static Stream<Arguments> provideWallets() {
        return wallets.stream()
                .map(Arguments::of);
    }

}