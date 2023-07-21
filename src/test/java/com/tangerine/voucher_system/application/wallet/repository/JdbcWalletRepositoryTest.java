package com.tangerine.voucher_system.application.wallet.repository;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.repository.JdbcCustomerRepository;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.repository.JdbcVoucherRepository;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
@Import({JdbcWalletRepository.class, JdbcVoucherRepository.class, JdbcCustomerRepository.class})
class JdbcWalletRepositoryTest {

    @Autowired
    WalletRepository repository;

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

        repository.insert(wallet);

        List<Wallet> result = repository.findByCustomerId(wallet.customerId());
        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 추가하면 실패한다.")
    @MethodSource("provideWallets")
    void insert_ParamExistWallet_Exception(Wallet wallet) {
        repository.insert(wallet);

        Exception exception = catchException(() -> repository.insert(wallet));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 업데이트하면 성공한다.")
    @MethodSource("provideWallets")
    void update_ParamExistWallet_UpdateWallet(Wallet wallet) {
        repository.insert(wallet);
        Voucher newVoucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 21), LocalDate.now());
        voucherRepository.insert(newVoucher);

        Wallet newWallet = new Wallet(wallet.walletId(), newVoucher.getVoucherId(), wallet.customerId());
        repository.update(newWallet);

        List<Wallet> result = repository.findByCustomerId(wallet.customerId());
        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 업데이트하면 실패한다.")
    @MethodSource("provideWallets")
    void update_ParamNotExistWallet_Exception(Wallet wallet) {

        Exception exception = catchException(() -> repository.update(wallet));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 삭제하면 성공한다.")
    @MethodSource("provideWallets")
    void deleteById_ParamExistWalletId_DeleteWallet(Wallet wallet) {
        repository.insert(wallet);

        repository.deleteById(wallet.walletId());

        boolean result = repository.findByCustomerId(wallet.customerId())
                .stream()
                .anyMatch(w -> Objects.equals(wallet.walletId(), w.walletId()));
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 고객 아이디로 조회하면 성공한다.")
    @MethodSource("provideWallets")
    void findByCustomerId_ParamExistWallet_ReturnWallet(Wallet wallet) {
        repository.insert(wallet);

        List<Wallet> result = repository.findByCustomerId(wallet.customerId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 고객 아이디로 조회하면 실패한다.")
    @MethodSource("provideWallets")
    void findByCustomerId_ParamNotExistWallet_ReturnEmptyList(Wallet wallet) {

        List<Wallet> wallets = repository.findByCustomerId(wallet.customerId());

        assertThat(wallets).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 바우처 아이디로 조회하면 성공한다.")
    @MethodSource("provideWallets")
    void findByVoucherId_ParamExistWallet_ReturnWallet(Wallet wallet) {
        repository.insert(wallet);

        List<Wallet> result = repository.findByVoucherId(wallet.voucherId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 바우처 아이디로 조회하면 실패한다.")
    @MethodSource("provideWallets")
    void findByVoucherId_ParamNotExistWallet_ReturnEmptyList(Wallet wallet) {

        List<Wallet> wallets = repository.findByVoucherId(wallet.voucherId());

        assertThat(wallets).isEmpty();
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