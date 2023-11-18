package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.util.UUIDUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static devcourse.springbootbasic.TestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryVoucherRepositoryTest {

    private InMemoryVoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository = new InMemoryVoucherRepository();
    }

    @Test
    @DisplayName("바우처 생성 시 생성된 바우처를 반환합니다.")
    void testSaveVoucher() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 100);

        // When
        Voucher savedVoucher = voucherRepository.save(voucher);

        // Then
        assertThat(savedVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있습니다.")
    void testFindAllVouchers() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        Voucher voucher1 = generateAssignedVoucher(VoucherType.FIXED, 100, customerId);
        Voucher voucher2 = generateAssignedVoucher(VoucherType.FIXED, 100, customerId);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // When
        List<Voucher> vouchers = voucherRepository.findAll();

        // Then
        assertThat(vouchers).hasSize(2);
    }

    @ParameterizedTest
    @MethodSource("devcourse.springbootbasic.TestDataFactory#searchConditionProvider")
    @DisplayName("바우처 생성기간 및 특정 할인타입별 바우처를 조회할 수 있습니다.")
    void testFindAllVoucherWithSearchConditions(VoucherType voucherType, LocalDate startDate, LocalDate endDate, int expectedSize) {
        // Given
        List<Voucher> voucherList = generateSampleVouchers();

        for (Voucher voucher : voucherList) {
            voucherRepository.save(voucher);
        }


        // When
        List<Voucher> vouchers = voucherRepository.findAllWithSearchConditions(voucherType, startDate, endDate);

        // Then
        assertThat(vouchers).hasSize(expectedSize);
    }

    @Test
    @DisplayName("ID로 바우처를 조회할 수 있습니다.")
    void testFindVoucherById() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 100);
        UUID voucherId = voucher.getId();
        voucherRepository.save(voucher);

        // When
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);

        // Then
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.orElseThrow().getId()).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("바우처 정보를 업데이트할 수 있습니다.")
    void testUpdateVoucher() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 100);
        UUID voucherId = voucher.getId();
        voucherRepository.save(voucher);

        // When
        voucher.updateDiscountValue(150);
        boolean updateResult = voucherRepository.update(voucher);

        // Then
        assertThat(updateResult).isTrue();
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(150);
    }

    @Test
    @DisplayName("바우처 정보가 없는 경우 업데이트하지 않습니다.")
    void testUpdateVoucherWhenVoucherDoesNotExist() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 100);

        // When
        boolean updateResult = voucherRepository.update(voucher);

        // Then
        assertThat(updateResult).isFalse();
    }

    @Test
    @DisplayName("바우처를 삭제할 수 있습니다.")
    void testDeleteVoucher() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 100);
        UUID voucherId = voucher.getId();
        voucherRepository.save(voucher);

        // When
        voucherRepository.delete(voucher);

        // Then
        assertThat(voucherRepository.findById(voucherId)).isEmpty();
    }
}
