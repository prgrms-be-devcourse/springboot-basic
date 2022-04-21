package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.converter.VoucherRequestConverter;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherRequestConverterTest {

    VoucherRequestConverter voucherRequestConverter = new VoucherRequestConverter();

    @Test
    @DisplayName("유효한 값으로 입력된 VoucherType과 DiscountValue으로 DTO 생성_성공")
    void convertVoucherRequestWithValidInput() {
        //given
        int voucherTypeNum = 1;
        int discountValue = 50;

        //then
        VoucherRequest request = voucherRequestConverter.of(voucherTypeNum, discountValue);

        //when
        assertThat(request.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        assertThat(request.getDiscountValue()).isEqualTo(discountValue);
    }

    @Test
    @DisplayName("유효하지 않은 값으로 입력된 VoucherType과 DiscountValue으로 DTO 생성_성공")
    void convertVoucherRequestWithInvalidInput() {
        //given
        int voucherTypeNum = 0;
        int discountValue = 50;

        //then
        VoucherRequest request = voucherRequestConverter.of(voucherTypeNum, discountValue);

        //when
        assertThat(request.getVoucherType()).isEqualTo(VoucherType.NONE);
        assertThat(request.getDiscountValue()).isEqualTo(discountValue);
    }
}
