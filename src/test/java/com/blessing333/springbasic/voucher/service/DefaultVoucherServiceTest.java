package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.domain.Voucher.VoucherType;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
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
        VoucherCreateForm form = new VoucherCreateForm(VoucherType.FIXED, discountAmount);

        Voucher newVoucher = service.registerVoucher(form);

        UUID id = newVoucher.getVoucherId();
        assertTrue(repository.findById(id).isPresent());
    }


    @DisplayName("비율 할인 바우처 생성 - 성공")
    @Test
    void createVoucherTestWithIncorrectForm() {
        long discountPercent = 50;
        VoucherCreateForm form = new VoucherCreateForm(VoucherType.PERCENT, discountPercent);

        Voucher newVoucher = service.registerVoucher(form);

        UUID id = newVoucher.getVoucherId();
        assertTrue(repository.findById(id).isPresent());
    }

    @DisplayName(" 모든 바우처 조회 - 성공")
    @Test
    void loadAllVoucherTest() {
        VoucherCreateForm form1 = new VoucherCreateForm(VoucherType.PERCENT, 50);
        VoucherCreateForm form2 = new VoucherCreateForm(VoucherType.FIXED, 5000);
        VoucherCreateForm form3 = new VoucherCreateForm(VoucherType.FIXED, 20000);
        Voucher newVoucher1 = service.registerVoucher(form1);
        Voucher newVoucher2 = service.registerVoucher(form2);
        Voucher newVoucher3 = service.registerVoucher(form3);

        List<Voucher> candi = List.of(newVoucher1, newVoucher2, newVoucher3);
        List<Voucher> vouchers = service.loadAllVoucher();

        assertThat(vouchers).hasSize(3).containsAll(candi);
    }
}