package org.programmers.spbw1.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.spbw1.voucher.Model.VoucherType;
import org.programmers.spbw1.voucher.Repository.MemoryVoucherRepository;
import org.programmers.spbw1.voucher.Repository.VoucherRepository;
import org.programmers.spbw1.voucher.service.VoucherService;
import org.programmers.spbw1.voucher.validator.VoucherValidator;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherTest {

    VoucherRepository repository = new MemoryVoucherRepository();
    VoucherService service = new VoucherService(repository);
    @Test
    @DisplayName("바우처 검증 테스트")
    public void voucherValidatorTest(){
        VoucherValidator fixedInRangeValidator = new VoucherValidator(VoucherType.FIXED, "1000");
        VoucherValidator fixedOutRangeValidator = new VoucherValidator(VoucherType.FIXED, "-12");

        VoucherValidator percentInRangeValidator = new VoucherValidator(VoucherType.PERCENT, "10");
        VoucherValidator percentOutRangeValidator = new VoucherValidator(VoucherType.PERCENT, "1000");

        assertThat(fixedInRangeValidator.validate()).isEqualTo(true);
        assertThat(fixedOutRangeValidator.validate()).isEqualTo(false);

        assertThat(percentInRangeValidator.validate()).isEqualTo(true);
        assertThat(percentOutRangeValidator.validate()).isEqualTo(false);
    }

    @Test
    @DisplayName("Service 바우처 생성 테스트")
    public void serviceCreateTest(){
    }
}
