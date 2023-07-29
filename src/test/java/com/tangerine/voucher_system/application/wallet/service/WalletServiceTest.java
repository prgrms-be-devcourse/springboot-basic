package com.tangerine.voucher_system.application.wallet.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.repository.JdbcCustomerRepository;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.global.exception.SqlException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.repository.JdbcVoucherRepository;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.wallet.repository.JdbcWalletRepository;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletResult;
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
    @MethodSource("provideWalletParams")
    void insert_ParamNotExistWallet_InsertWallet(WalletParam param) {

        service.createWallet(param);

        List<WalletResult> result = service.findWalletsByCustomerId(param.customerId());
        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 추가하면 실패한다.")
    @MethodSource("provideWalletParams")
    void insert_ParamExistWallet_Exception(WalletParam param) {
        service.createWallet(param);

        Exception exception = catchException(() -> service.createWallet(param));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 업데이트하면 성공한다.")
    @MethodSource("provideWalletParams")
    void update_ParamExistWallet_UpdateWallet(WalletParam param) {
        service.createWallet(param);
        Voucher newVoucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 21), LocalDate.now());
        voucherRepository.insert(newVoucher);

        WalletParam newWallet = new WalletParam(param.walletId(), newVoucher.voucherId(), param.customerId());
        service.updateWallet(newWallet);

        List<WalletResult> result = service.findWalletsByCustomerId(param.customerId());
        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 업데이트하면 실패한다.")
    @MethodSource("provideWalletParams")
    void update_ParamNotExistWallet_Exception(WalletParam param) {

        Exception exception = catchException(() -> service.updateWallet(param));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 삭제하면 성공한다.")
    @MethodSource("provideWalletParams")
    void deleteById_ParamExistWalletId_DeleteWallet(WalletParam param) {
        service.createWallet(param);

        service.deleteWalletById(param.walletId());

        boolean result = service.findWalletsByCustomerId(param.customerId())
                .stream()
                .anyMatch(walletResult -> Objects.equals(param.walletId(), walletResult.walletId()));
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 고객 아이디로 조회하면 성공한다.")
    @MethodSource("provideWalletParams")
    void findByCustomerId_ParamExistWallet_ReturnWallet(WalletParam param) {
        service.createWallet(param);

        List<WalletResult> result = service.findWalletsByCustomerId(param.customerId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 고객 아이디로 조회하면 실패한다.")
    @MethodSource("provideWalletParams")
    void findByCustomerId_ParamNotExistWallet_ReturnWallet(WalletParam param) {

        List<WalletResult> wallets = service.findWalletsByCustomerId(param.customerId());

        assertThat(wallets).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 지갑을 바우처 아이디로 조회하면 성공한다.")
    @MethodSource("provideWalletParams")
    void findByVoucherId_ParamExistWallet_ReturnWallet(WalletParam param) {
        service.createWallet(param);

        List<WalletResult> result = service.findWalletsByVoucherId(param.voucherId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 지갑을 바우처 아이디로 조회하면 실패한다.")
    @MethodSource("provideWalletParams")
    void findByVoucherId_ParamNotExistWallet_ReturnWallet(WalletParam param) {

        List<WalletResult> wallets = service.findWalletsByVoucherId(param.voucherId());

        assertThat(wallets).isEmpty();
    }

    @Test
    @DisplayName("바우처, 고객, 지갑이 존재할 때 고객 아이디로 조회 시 성공한다.")
    void findVouchersByCustomerId_ParamAllExist_ReturnVoucherList() {
        WalletParam wallet = new WalletParam(UUID.randomUUID(), vouchers.get(0).voucherId(), customers.get(0).customerId());
        service.createWallet(wallet);

        List<VoucherResult> vouchers = service.findVouchersByCustomerId(wallet.customerId());

        assertThat(vouchers).isNotEmpty();
    }

    @Test
    @DisplayName("바우처, 고객, 지갑이 존재할 때 바우처 아이디로 조회 시 성공한다.")
    void findCustomersByVoucherId_ParamAllExist_ReturnCustomerList() {
        WalletParam wallet = new WalletParam(UUID.randomUUID(), vouchers.get(0).voucherId(), customers.get(0).customerId());
        service.createWallet(wallet);

        List<CustomerResult> vouchers = service.findCustomersByVoucherId(wallet.voucherId());

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

    static List<WalletParam> walletParams = IntStream.range(0, vouchers.size())
            .mapToObj(i -> new WalletParam(UUID.randomUUID(), vouchers.get(i).voucherId(), customers.get(i).customerId()))
            .toList();

    static Stream<Arguments> provideWalletParams() {
        return walletParams.stream()
                .map(Arguments::of);
    }

}