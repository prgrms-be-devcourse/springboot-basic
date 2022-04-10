package org.prgrms.kdt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.models.Voucher;
import org.prgrms.kdt.repository.MemoryVoucherRepository;

import java.util.List;

public class VoucherServiceTest {

    VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());

    @Test
    @DisplayName("FixedAmountVoucher 생성기능")
    public  void fixedAmountVoucherCreateTest(){

        //given

        //when
        Voucher voucher = voucherService.create(10, "fixed");
        List<Voucher> list = voucherService.voucherList();

        //then
        Assertions.assertThat(voucher).isEqualTo(list.get(0));
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성기능")
    public  void percentDiscountVoucherCreateTest(){

        //given

        //when
        Voucher voucher = voucherService.create(10, "percent");
        List<Voucher> list = voucherService.voucherList();

        //then
        Assertions.assertThat(voucher).isEqualTo(list.get(0));
    }

    @Test
    @DisplayName("바우처 목록 조회 확인")
    public void voucherListTest(){

        //given
        Voucher voucher1 = voucherService.create(10, "fixed");
        Voucher voucher2 = voucherService.create(11, "percent");

        //when
        List<Voucher> list = voucherService.voucherList();

        //then
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.get(0)).isEqualTo(voucher1);
        Assertions.assertThat(list.get(1)).isEqualTo(voucher2);
    }

    @Test
    @DisplayName("바우처 목록이 비어있을 경우")
    public void voucherListEmptyTest(){

        //given

        //when
        List<Voucher> list = voucherService.voucherList();

        //then
        Assertions.assertThat(list.isEmpty()).isEqualTo(true);
    }
}
