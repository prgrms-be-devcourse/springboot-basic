package com.wonu606.vouchermanager.service.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucher.factory.util.UUIDGenerator;
import com.wonu606.vouchermanager.service.voucher.factory.VoucherFactory;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("VoucherFactory 테스트")
public class VoucherFactoryTest {

    private UUIDGenerator uuidGenerator;
    private VoucherFactory factory;

    @BeforeEach
    void setup() {
        uuidGenerator = Mockito.mock(UUIDGenerator.class);
        factory = new VoucherFactory(uuidGenerator);
    }

    @DisplayName("create_type이 FIXED이면_FixedAmountVoucher를 생성한다.")
    @Test
    void Create_TypeIsFIXED_CreatesFixedAmountVoucher() {
        // Given
        UUID testUuid = UUID.randomUUID();
        given(uuidGenerator.generateUUID()).willReturn(testUuid);
        double discountValue = 10.0;
        VoucherCreateParam voucherCreateParam = new VoucherCreateParam("FIXED", discountValue);

        // When
        Voucher voucher = factory.create(voucherCreateParam);

        // Then
        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(voucher.getUuid()).isEqualTo(testUuid);
        assertThat(voucher.getDiscountValue())
                .isEqualTo(discountValue);
    }

    @DisplayName("create_type이 PERCENT이면_PercentageVoucher를 생성한다.")
    @Test
    void Create_TypeIsPERCENT_CreatesPercentageVoucher() {
        // Given
        UUID testUuid = UUID.randomUUID();
        given(uuidGenerator.generateUUID()).willReturn(testUuid);
        double discountValue = 10.0;
        VoucherCreateParam voucherCreateParam = new VoucherCreateParam("PERCENT", discountValue);

        // When
        Voucher voucher = factory.create(voucherCreateParam);

        // Then
        assertThat(voucher).isInstanceOf(PercentageVoucher.class);
        assertThat(voucher.getUuid()).isEqualTo(testUuid);
        assertThat(voucher.getDiscountValue())
                .isEqualTo(discountValue);
    }

    @DisplayName("create_type이 존재하지 않는다면_예외가 발생한다.")
    @Test
    void Create_TypeDoesNotExist_ThrowsException() {
        // Given
        UUID uuid = UUID.randomUUID();
        double discountValue = 10.0;
        VoucherCreateParam voucherCreateParam = new VoucherCreateParam("INVALID", discountValue);

        // When & Then
        assertThatThrownBy(() -> factory.create(voucherCreateParam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 바우처 타입입니다.");
    }
}
