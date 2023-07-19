package com.tangerine.voucher_system.application.voucher.repository;

import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@ActiveProfiles("dev")
class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository voucherRepository;

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), LocalDate.now())),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 2), LocalDate.now()))
        );
    }

    @BeforeEach
    void init() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @ParameterizedTest
    @DisplayName("바우처 생성 시 바우처맵에 추가되면 성공한다.")
    @MethodSource("provideVouchers")
    void insert_ParamVoucher_InsertAndReturnVoucher(Voucher voucher) {
        Voucher result = voucherRepository.insert(voucher);

        assertThat(result).isSameAs(voucher);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideVouchers")
    void update_ParamExistVoucher_UpdateAndReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Voucher result = voucherRepository.update(voucher);

        assertThat(result.voucherId()).isSameAs(voucher.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 갱신 시 실패한다.")
    @MethodSource("provideVouchers")
    void update_ParamNotExistVoucher_Exception(Voucher voucher) {

        Exception exception = catchException(() -> voucherRepository.update(voucher));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("바우처 리스트 반환 시 성공한다.")
    @MethodSource("provideVouchers")
    void findAll_ParamVoucher_ReturnVoucherList(Voucher voucher) {
        voucherRepository.insert(voucher);

        List<Voucher> result = voucherRepository.findAll();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("바우처를 아이디로 조회하는 경우 성공한다.")
    @MethodSource("provideVouchers")
    void findById_ParamExistVoucher_ReturnVoucherOrNull(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.voucherId());

        assertThat(foundVoucher).containsSame(voucher);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 조회하는 경우 실패한다.")
    @MethodSource("provideVouchers")
    void findById_ParamNotExistVoucher_EmptyOptional(Voucher voucher) {

        Optional<Voucher> maybeNull = voucherRepository.findById(voucher.voucherId());

        assertThat(maybeNull).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 생성일자로 조회하는 경우 성공한다.")
    @MethodSource("provideVouchers")
    void findByCreated_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        List<Voucher> result = voucherRepository.findByCreatedAt(voucher.createdAt());

        assertThat(result).isNotEmpty();
        assertThat(result).contains(voucher);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 생성일자로 조회하는 경우 성공한다.")
    @MethodSource("provideVouchers")
    void findByCreated_ParamNotExistVoucher_ReturnVoucher(Voucher voucher) {

        List<Voucher> result = voucherRepository.findByCreatedAt(voucher.createdAt());

        assertThat(result).isEmpty();
    }

    @Order(5)
    @ParameterizedTest
    @DisplayName("아이디로 바우처 제거한다.")
    @MethodSource("provideVouchers")
    void deleteById_ParamVoucher_DeleteVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        voucherRepository.deleteById(voucher.voucherId());

        Optional<Voucher> maybeNull = voucherRepository.findById(voucher.voucherId());
        assertThat(maybeNull).isEmpty();
    }

}