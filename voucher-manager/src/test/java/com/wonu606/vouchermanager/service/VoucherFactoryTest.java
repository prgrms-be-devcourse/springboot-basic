package com.wonu606.vouchermanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VoucherFactory 테스트")
public class VoucherFactoryTest {

    @DisplayName("create_type이 FIXED이면_FixedAmountVoucher를 생성한다.")
    @Test
    public void Create_TypeIsFIXED_CreatesFixedAmountVoucher() {
        // given
        VoucherFactory factory = new VoucherFactory();
        UUID uuid = UUID.randomUUID();
        double discountValue = 10.0;
        VoucherDto voucherDto = new VoucherDto("FIXED", uuid, discountValue);

        // Act
        Voucher voucher = factory.create(voucherDto);

        // Assert
        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(voucher.getUuid()).isEqualTo(uuid);
        assertThat(voucher.getDiscountValue().getValue())
                .isEqualTo(discountValue);
    }

    @DisplayName("create_type이 PERCENT이면_PercentageVoucher를 생성한다.")
    @Test
    public void Create_TypeIsPERCENT_CreatesPercentageVoucher() {
        // Arrange
        VoucherFactory factory = new VoucherFactory();
        UUID uuid = UUID.randomUUID();
        double discountValue = 10.0;
        VoucherDto voucherDto = new VoucherDto("PERCENT", uuid, discountValue);

        // Act
        Voucher voucher = factory.create(voucherDto);

        // Assert
        assertThat(voucher).isInstanceOf(PercentageVoucher.class);
        assertThat(voucher.getUuid()).isEqualTo(uuid);
        assertThat(voucher.getDiscountValue().getValue())
                .isEqualTo(discountValue);
    }

    @DisplayName("create_type이 존재하지 않는다면_예외가 발생한다.")
    @Test
    public void Create_TypeDoesNotExist_ThrowsException() {
        // Arrange
        VoucherFactory factory = new VoucherFactory();
        UUID uuid = UUID.randomUUID();
        double discountValue = 10.0;
        VoucherDto voucherDto = new VoucherDto("INVALID", uuid, discountValue);

        // Act and Assert
        assertThatThrownBy(() -> factory.create(voucherDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 바우처 타입입니다.");
    }
}
