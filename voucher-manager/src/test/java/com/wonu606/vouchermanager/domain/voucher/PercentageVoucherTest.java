package com.wonu606.vouchermanager.domain.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.domain.price.Price;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PercentageVoucher 테스트")
public class PercentageVoucherTest {

    @DisplayName("calculateDiscountedPrice_유효한퍼센트할인이라면_할인된가격을반환한다")
    @Test
    public void calculateDiscountedPrice_ValidPercentageDiscount_ReturnsDiscountedPrice() {
        // given
        UUID uuid = UUID.randomUUID();
        PercentageDiscountValue percentageDiscountValue = new PercentageDiscountValue(20);
        PercentageVoucher voucher = new PercentageVoucher(uuid, percentageDiscountValue);
        Price originalPrice = new Price(200);

        // when
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertThat(discountedPrice).isEqualTo(
                originalPrice.getValue() * (100 - percentageDiscountValue.getValue()) / 100);
    }

    @DisplayName("calculateDiscountedPrice_100퍼센트할인이라면_무조건0을반환한다")
    @Test
    public void calculateDiscountedPrice_HundredPercentageDiscount_ReturnsZero() {
        // given
        UUID uuid = UUID.randomUUID();
        PercentageDiscountValue percentageDiscountValue = new PercentageDiscountValue(100);
        PercentageVoucher voucher = new PercentageVoucher(uuid, percentageDiscountValue);
        Price originalPrice = new Price(200);

        // when
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertThat(discountedPrice).isEqualTo(0);
    }
}
