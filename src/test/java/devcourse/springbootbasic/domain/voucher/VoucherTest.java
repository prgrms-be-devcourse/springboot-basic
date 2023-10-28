package devcourse.springbootbasic.domain.voucher;

import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import devcourse.springbootbasic.util.UUIDUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static devcourse.springbootbasic.TestDataFactory.generateAssignedVoucher;
import static devcourse.springbootbasic.TestDataFactory.generateUnassignedVoucher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


class VoucherTest {

    @Test
    @DisplayName("할인 쿠폰을 생성할 수 있어야 합니다.")
    void testCreateVoucher() {
        // When
        UUID customerId = UUIDUtil.generateRandomUUID();
        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 100, customerId);
        UUID voucherId = voucher.getId();

        // Then
        assertThat(voucher.getId()).isEqualTo(voucherId);
        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.FIXED);
        assertThat(voucher.getDiscountValue()).isEqualTo(100);
        assertThat(voucher.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("할인 쿠폰을 생성할 때 할인 금액이 유효한지 검증합니다.")
    void testCreateVoucherWithInvalidDiscountValue() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        long invalidDiscountValue = -50;

        // When and Then
        assertThatExceptionOfType(VoucherException.class)
                .isThrownBy(() -> generateAssignedVoucher(VoucherType.FIXED, invalidDiscountValue, customerId))
                .withMessageContaining(VoucherErrorMessage.INVALID_DISCOUNT_VALUE.getMessage());
    }

    @Test
    @DisplayName("할인 쿠폰의 할인 금액을 수정할 수 있어야 합니다.")
    void testUpdateDiscountValue() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 100, customerId);
        long newDiscountValue = 200;

        // When
        Voucher updatedVoucher = voucher.updateDiscountValue(newDiscountValue);

        // Then
        assertThat(updatedVoucher.getDiscountValue()).isEqualTo(newDiscountValue);
    }

    @Test
    @DisplayName("할인 쿠폰의 할인 금액을 수정할 때 할인 금액이 유효한지 검증합니다.")
    void testUpdateDiscountValueWithInvalidValue() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 100, customerId);
        long invalidDiscountValue = -50;

        // When and Then
        assertThatExceptionOfType(VoucherException.class)
                .isThrownBy(() -> voucher.updateDiscountValue(invalidDiscountValue))
                .withMessageContaining(VoucherErrorMessage.INVALID_DISCOUNT_VALUE.getMessage());
    }

    @Test
    @DisplayName("할인 쿠폰을 고객에게 할당할 수 있어야 합니다.")
    void testAssignToCustomer() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 100);

        // When
        Voucher assignedVoucher = voucher.assignToCustomer(customerId);

        // Then
        assertThat(assignedVoucher.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("할인 쿠폰을 고객에게 할당할 때 이미 할당된 쿠폰인 경우 할당할 수 없습니다.")
    void testUnassignToCustomer() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 100, customerId);

        // When
        Voucher unassignedVoucher = voucher.unassignToCustomer();

        // Then
        assertThat(unassignedVoucher.getCustomerId()).isNull();
    }

    @Test
    @DisplayName("할인 쿠폰이 고객에게 할당되어 있는지 확인할 수 있어야 합니다.")
    void testIsAssigned() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        Voucher voucherWithCustomer = generateAssignedVoucher(VoucherType.FIXED, 100, customerId);
        Voucher voucherWithoutCustomer = generateAssignedVoucher(VoucherType.FIXED, 100, null);

        // When
        boolean isAssignedToCustomer = voucherWithCustomer.isAssigned();
        boolean isAssignedWithoutCustomer = voucherWithoutCustomer.isAssigned();

        // Then
        assertThat(isAssignedToCustomer).isTrue();
        assertThat(isAssignedWithoutCustomer).isFalse();
    }
}
