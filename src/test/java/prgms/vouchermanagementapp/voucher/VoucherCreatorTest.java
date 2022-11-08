package prgms.vouchermanagementapp.voucher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherCreatorTest {

    VoucherCreator voucherCreator = new VoucherCreator();

    @Test
    @DisplayName("유효하지 않은 voucherTypeIndex에 대해서 오류가 발생한다.")
    void should_throw_exception_for_invalid_voucherTypeIndex() {
        // when
        String invalidVoucherTypeIndex = "3";

        // then
        Assertions.assertThrows(NoSuchFieldError.class, () -> {
            voucherCreator.create(invalidVoucherTypeIndex);
        });
    }
}