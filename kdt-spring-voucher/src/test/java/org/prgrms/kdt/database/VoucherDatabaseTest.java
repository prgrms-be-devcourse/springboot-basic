package org.prgrms.kdt.database;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

class VoucherDatabaseTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);

    @Test
    void saveVoucher() {
        //given
        VoucherDatabase voucherDatabase = ac.getBean(VoucherDatabase.class);

        //when
        voucherDatabase.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10));
        voucherDatabase.saveVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 20));

        //then
        Assertions.assertThat(voucherDatabase.findAllVoucher().get(0))
                .isInstanceOf(FixedAmountVoucher.class);
        Assertions.assertThat(voucherDatabase.findAllVoucher().get(1))
                .isInstanceOf(PercentDiscountVoucher.class);
        Assertions.assertThat(voucherDatabase.findAllVoucher().size())
                .isEqualTo(2);
    }

}