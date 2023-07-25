package com.wonu606.vouchermanager.domain.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.domain.voucher.price.Price;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PercentageVoucher 테스트")
public class PercentageVoucherTest {

    @DisplayName("calculateDiscountedPrice_유효한 퍼센트 할인이라면_할인된 가격을 반환한다.")
    @Test
    public void calculateDiscountedPrice_ValidPercentageDiscount_ReturnsDiscountedPrice() {
        // Given
        UUID uuid = UUID.randomUUID();
        PercentageDiscountValue percentageDiscountValue = new PercentageDiscountValue(20);
        PercentageVoucher voucher = new PercentageVoucher(uuid, percentageDiscountValue);
        Price originalPrice = new Price(200);

        // When
        Price discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // Then
        assertThat(discountedPrice.getValue()).isEqualTo(
                originalPrice.getValue() * (100 - percentageDiscountValue.getValue()) / 100);
    }

    @DisplayName("calculateDiscountedPrice_100퍼센트 할인이라면_무조건 0을 반환한다.")
    @Test
    public void calculateDiscountedPrice_HundredPercentageDiscount_ReturnsZero() {
        // Given
        UUID uuid = UUID.randomUUID();
        PercentageDiscountValue percentageDiscountValue = new PercentageDiscountValue(100);
        PercentageVoucher voucher = new PercentageVoucher(uuid, percentageDiscountValue);
        Price originalPrice = new Price(200);

        // When
        Price discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // Then
        assertThat(discountedPrice.getValue()).isEqualTo(0);
    }
}
