package com.wonu606.vouchermanager.domain.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.price.Price;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FixedAmountVoucher 테스트")
class FixedAmountVoucherTest {

    @DisplayName("calculateDiscountedPrice_할인가격이 원래가격보다 적을때_할인된 가격을 반환한다.")
    @Test
    public void calculateDiscountedPrice_DiscountLessThanOriginalPrice_ReturnsDiscountedPrice() {
        // Given
        UUID uuid = UUID.randomUUID();
        FixedAmountValue fixedAmountValue = new FixedAmountValue(50);
        FixedAmountVoucher voucher = new FixedAmountVoucher(uuid, fixedAmountValue);
        Price originalPrice = new Price(100);

        // When
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // Then
        assertThat(discountedPrice).isEqualTo(
                originalPrice.getValue() - fixedAmountValue.getValue());
    }

    @DisplayName("calculateDiscountedPrice_할인가격이 원래가격보다 많을때_0을 반환한다.")
    @Test
    public void calculateDiscountedPrice_DiscountGreaterThanOriginalPrice_ReturnsZero() {
        // Given
        UUID uuid = UUID.randomUUID();
        FixedAmountValue fixedAmountValue = new FixedAmountValue(150);
        FixedAmountVoucher voucher = new FixedAmountVoucher(uuid, fixedAmountValue);
        Price originalPrice = new Price(100);

        // When
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // Then
        assertThat(discountedPrice).isEqualTo(0);
    }
}
