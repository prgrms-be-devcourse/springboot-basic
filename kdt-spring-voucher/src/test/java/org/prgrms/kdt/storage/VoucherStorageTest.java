package org.prgrms.kdt.storage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class VoucherStorageTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);


    @AfterEach
    void afterEach(){
        VoucherStorage voucherStorage = ac.getBean(VoucherStorage.class);
        voucherStorage.clearStorage();
    }
    @Test
    void save() {
        //given
        VoucherStorage voucherStorage = ac.getBean(VoucherStorage.class);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        //when
        voucherStorage.saveVoucher(percentDiscountVoucher);

        //then
        assertThat(voucherStorage.findAllVoucher().get(0))
                .isInstanceOf(PercentDiscountVoucher.class);
    }

    @Test
    void saveAll(){
        //given
        VoucherStorage voucherStorage = ac.getBean(VoucherStorage.class);
        List<Voucher> voucherList = List.of(new PercentDiscountVoucher(UUID.randomUUID(), 30),
            new FixedAmountVoucher(UUID.randomUUID(), 40));

        //when
        voucherList.stream().forEach(voucherStorage::saveVoucher);

        //then
        assertThat(voucherStorage.findAllVoucher()).containsExactly(voucherList.get(0), voucherList.get(1));
    }
}
