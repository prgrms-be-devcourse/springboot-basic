package com.tangerine.voucher_system.application.voucher.repository;

import com.tangerine.voucher_system.application.customer.repository.CustomerRepository;
import com.tangerine.voucher_system.application.customer.repository.JdbcCustomerRepository;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@JdbcTest
@ActiveProfiles("test")
@Import({JdbcVoucherRepository.class, JdbcCustomerRepository.class})
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 추가 시 성공한다.")
    @MethodSource("provideVouchers")
    void insert_ParamNotExistVoucher_InsertAndReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(foundVoucher).isNotEmpty();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 추가 시 실패한다.")
    @MethodSource("provideVouchers")
    void insert_ParamExistVoucher_Exception(Voucher voucher) {
        voucherRepository.insert(voucher);

        Exception exception = catchException(() -> voucherRepository.insert(voucher));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideVouchers")
    void update_ParamExistVoucher_UpdateAndReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
        Voucher newVoucher = new Voucher(voucher.getVoucherId(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), LocalDateTime.now(), voucher.getCustomerId());

        voucherRepository.update(newVoucher);
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(foundVoucher).isNotEmpty();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(newVoucher.getDiscountValue());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 갱신 시 실패한다.")
    @MethodSource("provideVouchers")
    void update_ParamNotExistVoucher_Exception(Voucher voucher) {
        Exception exception = catchException(() -> voucherRepository.update(voucher));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @Test
    @DisplayName("모든 바우처 조회한다.")
    void findAllVouchers_ParamVoid_ReturnAllVouchers() {
        vouchers.forEach(voucher -> voucherRepository.insert(voucher));

        List<Voucher> allVouchers = voucherRepository.findAll();

        assertThat(allVouchers).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 조회 시 성공한다.")
    @MethodSource("provideVouchers")
    void findById_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(foundVoucher).isNotEmpty();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 아이디로 조회 시 실패한다.")
    @MethodSource("provideVouchers")
    void findById_ParamNotExistVoucher_ReturnOptionalEmpty(Voucher voucher) {
        Optional<Voucher> result = voucherRepository.findById(voucher.getVoucherId());

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 바우처 타입으로 조회 시 성공한다.")
    @MethodSource("provideVouchers")
    void findByVoucherType_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findByVoucherType(voucher.getVoucherType());

        assertThat(foundVoucher).isNotEmpty();
        assertThat(foundVoucher.get().getVoucherType()).isEqualTo(voucher.getVoucherType());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 바우처 타입으로 조회 시 실패한다.")
    @MethodSource("provideVouchers")
    void findByVoucherType_ParamNotExistVoucher_ReturnOptionalEmpty(Voucher voucher) {

        Optional<Voucher> result = voucherRepository.findByVoucherType(voucher.getVoucherType());

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 생성일자로 조회 시 실패한다.")
    @MethodSource("provideVouchers")
    void findByCreatedAt_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> result = voucherRepository.findByCreatedAt(voucher.getCreatedAt());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 생성일자로 조회 시 실패한다.")
    @MethodSource("provideVouchers")
    void findByCreatedAt_ParamNotExistVoucher_ReturnOptionalEmpty(Voucher voucher) {

        Optional<Voucher> result = voucherRepository.findByCreatedAt(voucher.getCreatedAt());

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 제거하면 성공한다.")
    @MethodSource("provideVouchers")
    void deleteById_ParamExistVoucher_DeleteVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        voucherRepository.deleteById(voucher.getVoucherId());

        Optional<Voucher> maybeNull = voucherRepository.findById(voucher.getVoucherId());
        assertThat(maybeNull).isEmpty();
    }

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"), LocalDateTime.now(), Optional.empty()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "2"), LocalDateTime.now(), Optional.empty())
    );

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

}
