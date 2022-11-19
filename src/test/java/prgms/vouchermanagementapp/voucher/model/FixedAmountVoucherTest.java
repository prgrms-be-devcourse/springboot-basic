package prgms.vouchermanagementapp.voucher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import prgms.vouchermanagementapp.VoucherManagementApp;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.model.Amount;
import prgms.vouchermanagementapp.voucher.VoucherCreationFactory;

class FixedAmountVoucherTest {
    
    static AnnotationConfigApplicationContext applicationContext;

    @DisplayName("프로파일 설정")
    @BeforeAll
    static void beforeEach() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(VoucherManagementApp.class);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("release");
        applicationContext.refresh();
    }

    @DisplayName("할인 전 금액이 고정 할인 금액보다 작은 경우 IllegalStateException 발생")
    @Test
    void should_throw_exception_when_amount_is_lower_than_fixedDiscountAmount() {
        // given
        long amountBeforeDiscount = 2000;
        Amount fixedDiscountAmount = new Amount(3000);

        // when
        Voucher voucher = VoucherCreationFactory.createVoucher(fixedDiscountAmount);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            voucher.discount(amountBeforeDiscount);
        });
    }
}