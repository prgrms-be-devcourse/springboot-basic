package devcourse.springbootbasic.domain.voucher;

import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class VoucherTest {

    private UUID voucherId;
    private VoucherType voucherType;
    private long initialDiscountValue;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        voucherId = UUID.randomUUID();
        voucherType = VoucherType.FIXED;
        initialDiscountValue = 100;
        customerId = UUID.randomUUID();
    }

    @Test
    @DisplayName("할인 쿠폰을 생성할 수 있어야 합니다.")
    void testCreateVoucher() {
        // Given
        // Set up the initial parameters

        // When
        Voucher voucher = Voucher.createVoucher(voucherId, voucherType, initialDiscountValue, customerId);

        // Then
        assertThat(voucher.getId()).isEqualTo(voucherId);
        assertThat(voucher.getVoucherType()).isEqualTo(voucherType);
        assertThat(voucher.getDiscountValue()).isEqualTo(initialDiscountValue);
        assertThat(voucher.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("할인 쿠폰을 생성할 때 할인 금액이 유효한지 검증합니다.")
    void testCreateVoucherWithInvalidDiscountValue() {
        // Given
        // Attempt to create a voucher with an invalid discount value
        long invalidDiscountValue = -50;

        // When and Then
        assertThatExceptionOfType(VoucherException.class)
                .isThrownBy(() -> Voucher.createVoucher(voucherId, voucherType, invalidDiscountValue, customerId))
                .withMessageContaining(VoucherErrorMessage.INVALID_DISCOUNT_VALUE.getMessage());
    }

    @Test
    @DisplayName("할인 쿠폰의 할인 금액을 수정할 수 있어야 합니다.")
    void testUpdateDiscountValue() {
        // Given
        Voucher voucher = Voucher.createVoucher(voucherId, voucherType, initialDiscountValue, customerId);
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
        Voucher voucher = Voucher.createVoucher(voucherId, voucherType, initialDiscountValue, customerId);
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
        Voucher voucher = Voucher.createVoucher(voucherId, voucherType, initialDiscountValue, null);

        // When
        Voucher assignedVoucher = voucher.assignToCustomer(customerId);

        // Then
        assertThat(assignedVoucher.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("할인 쿠폰을 고객에게 할당할 때 이미 할당된 쿠폰인 경우 할당할 수 없습니다.")
    void testUnassignToCustomer() {
        // Given
        Voucher voucher = Voucher.createVoucher(voucherId, voucherType, initialDiscountValue, customerId);

        // When
        Voucher unassignedVoucher = voucher.unassignToCustomer();

        // Then
        assertThat(unassignedVoucher.getCustomerId()).isNull();
    }

    @Test
    @DisplayName("할인 쿠폰이 고객에게 할당되어 있는지 확인할 수 있어야 합니다.")
    void testIsAssigned() {
        // Given
        Voucher voucherWithCustomer = Voucher.createVoucher(voucherId, voucherType, initialDiscountValue, customerId);
        Voucher voucherWithoutCustomer = Voucher.createVoucher(voucherId, voucherType, initialDiscountValue, null);

        // Then
        assertThat(voucherWithCustomer.isAssigned()).isTrue();
        assertThat(voucherWithoutCustomer.isAssigned()).isFalse();
    }
}
