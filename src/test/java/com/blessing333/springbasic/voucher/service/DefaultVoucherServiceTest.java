package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.domain.FixedAmountVoucher;
import com.blessing333.springbasic.voucher.domain.PercentDiscountVoucher;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.repository.MemoryVoucherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultVoucherServiceTest {
    private final MemoryVoucherRepository repository = new MemoryVoucherRepository();
    private final DefaultVoucherService service = new DefaultVoucherService(repository);

    @AfterEach
    void resetRepository(){
        repository.deleteAll();
    }

    @DisplayName("고정 금액 할인 바우처 생성 - 성공")
    @Test
    void createFixedVoucherTest() {
        long discountAmount = 5000;
        ConvertedVoucherCreateForm form = new ConvertedVoucherCreateForm(VoucherType.FIXED, discountAmount);

        Voucher newVoucher = service.createNewVoucher(form);

        UUID id = newVoucher.getVoucherId();
        assertTrue(newVoucher instanceof FixedAmountVoucher);
        assertThat(((FixedAmountVoucher) newVoucher).getDiscountAmount()).isEqualTo(discountAmount);
        assertTrue(repository.findById(id).isPresent());
    }


    @DisplayName("비율 할인 바우처 생성 - 성공")
    @Test
    void createVoucherTestWithIncorrectForm() {
        long discountPercent = 50;
        ConvertedVoucherCreateForm form = new ConvertedVoucherCreateForm(VoucherType.PERCENT, discountPercent);

        Voucher newVoucher = service.createNewVoucher(form);

        UUID id = newVoucher.getVoucherId();
        assertTrue(newVoucher instanceof PercentDiscountVoucher);
        assertThat(((PercentDiscountVoucher) newVoucher).getPercent()).isEqualTo(discountPercent);
        assertTrue(repository.findById(id).isPresent());
    }

    @DisplayName(" 모든 바우처 조회 - 성공")
    @Test
    void loadAllVoucherTest() {
        ConvertedVoucherCreateForm form1 = new ConvertedVoucherCreateForm(VoucherType.PERCENT, 50);
        ConvertedVoucherCreateForm form2 = new ConvertedVoucherCreateForm(VoucherType.FIXED, 5000);
        ConvertedVoucherCreateForm form3 = new ConvertedVoucherCreateForm(VoucherType.FIXED, 20000);
        Voucher newVoucher1 = service.createNewVoucher(form1);
        Voucher newVoucher2 = service.createNewVoucher(form2);
        Voucher newVoucher3 = service.createNewVoucher(form3);

        List<Voucher> candi = List.of(newVoucher1, newVoucher2, newVoucher3);
        List<Voucher> vouchers = service.loadAllVoucher();

        assertThat(vouchers).hasSize(3).containsAll(candi);
    }
}