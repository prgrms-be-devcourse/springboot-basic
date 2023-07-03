package com.wonu606.vouchermanager.domain.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.price.Price;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FixedAmountVoucher 테스트")
class FixedAmountVoucherTest {

    @DisplayName("calculateDiscountedPrice_할인가격이원래가격보다적을때_할인된가격을반환한다")
    @Test
    public void calculateDiscountedPrice_DiscountLessThanOriginalPrice_ReturnsDiscountedPrice() {
        // given
        UUID uuid = UUID.randomUUID();
        FixedAmountValue fixedAmountValue = new FixedAmountValue(50);
        FixedAmountVoucher voucher = new FixedAmountVoucher(uuid, fixedAmountValue);
        Price originalPrice = new Price(100);

        // when
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertThat(discountedPrice).isEqualTo(
                originalPrice.getValue() - fixedAmountValue.getValue());
    }

    @DisplayName("calculateDiscountedPrice_할인가격이원래가격보다많을때_0을반환한다")
    @Test
    public void calculateDiscountedPrice_DiscountGreaterThanOriginalPrice_ReturnsZero() {
        // given
        UUID uuid = UUID.randomUUID();
        FixedAmountValue fixedAmountValue = new FixedAmountValue(150);
        FixedAmountVoucher voucher = new FixedAmountVoucher(uuid, fixedAmountValue);
        Price originalPrice = new Price(100);

        // when
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertThat(discountedPrice).isEqualTo(0);
    }
}
