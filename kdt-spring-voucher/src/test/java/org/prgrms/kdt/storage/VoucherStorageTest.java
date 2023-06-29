package org.prgrms.kdt.storage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

class VoucherStorageTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);

    @Test
    void saveVoucher() {
        //given
        VoucherStorage voucherStorage = ac.getBean(VoucherStorage.class);
        List<Voucher> voucherList = List.of(new FixedAmountVoucher(UUID.randomUUID(), 10),
                new PercentDiscountVoucher(UUID.randomUUID(), 20));

        //when
        voucherList.stream().forEach(voucherStorage::saveVoucher);

        //then
        Assertions.assertThat(voucherStorage.findAllVoucher().get(0))
                .isInstanceOf(FixedAmountVoucher.class);
        Assertions.assertThat(voucherStorage.findAllVoucher().get(1))
                .isInstanceOf(PercentDiscountVoucher.class);
        Assertions.assertThat(voucherStorage.findAllVoucher().size())
                .isEqualTo(2);
    }
}
