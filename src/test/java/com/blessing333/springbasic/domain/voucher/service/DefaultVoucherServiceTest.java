package com.blessing333.springbasic.domain.voucher.service;

import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.domain.voucher.dto.VoucherUpdateForm;
import com.blessing333.springbasic.domain.voucher.model.Voucher;
import com.blessing333.springbasic.domain.voucher.model.Voucher.VoucherType;
import com.blessing333.springbasic.domain.voucher.repository.VoucherRepository;
import com.blessing333.springbasic.domain.voucher.service.exception.VoucherDeleteFailException;
import com.blessing333.springbasic.domain.voucher.service.exception.VoucherFindFailException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc

class DefaultVoucherServiceTest {
    @Autowired
    private VoucherService service;

    @Autowired
    private VoucherRepository repository;

    @DisplayName("고정 금액 할인 바우처 생성 - 성공")
    @Test
    void createFixedVoucherTest() {
        long discountAmount = 5000;
        VoucherCreateForm form = new VoucherCreateForm(VoucherType.FIXED, discountAmount);

        Voucher newVoucher = service.registerVoucher(form);

        UUID id = newVoucher.getVoucherId();
        assertTrue(repository.findById(id).isPresent());
    }

    @DisplayName("모든 바우처 정보를 조회할 수 있어야한다")
    @Test
    void loadAllVoucherTest() {
        Voucher newVoucher1 = registerVoucher(VoucherType.PERCENT, 50);
        Voucher newVoucher2 = registerVoucher(VoucherType.FIXED, 5000);
        Voucher newVoucher3 = registerVoucher(VoucherType.FIXED, 20000);

        List<Voucher> candi = List.of(newVoucher1, newVoucher2, newVoucher3);
        List<Voucher> vouchers = service.loadAllVoucher();

        assertThat(vouchers).hasSize(3).containsAll(candi);
    }

    @DisplayName("ID로 바우처 정보를 조회할 수 있어야한다")
    @Test
    void loadVoucherTest(){
        Voucher newVoucher = registerVoucher(VoucherType.PERCENT, 50);

        Voucher found = service.loadVoucherById(newVoucher.getVoucherId());

        assertThat(newVoucher).isEqualTo(found);
        assertThat(newVoucher.getVoucherType()).isEqualTo(found.getVoucherType());
        assertThat(newVoucher.getDiscountAmount()).isEqualTo(found.getDiscountAmount());
    }

    @DisplayName("존재하지 않는 바우처에 대해 조회를 시도하면 VoucherFindFailException을 던진다")
    @Test
    void loadVoucherFailTest(){
        UUID invalidId = UUID.randomUUID();

        Assertions.assertThrows(VoucherFindFailException.class,()->service.loadVoucherById(invalidId));
    }

    @DisplayName("바우처를 삭제할 수 있어야한다")
    @Test
    void voucherDeleteTest(){
        Voucher newVoucher = registerVoucher(VoucherType.PERCENT, 50);

        service.deleteVoucher(newVoucher.getVoucherId());

        Assertions.assertThrows(VoucherFindFailException.class,()->service.loadVoucherById(newVoucher.getVoucherId()));
    }

    @DisplayName("존재하지 않는 바우처 삭제 시도할 경우 VoucherDeleteFailException을 던진다")
    @Test
    void voucherDeleteFailTest(){
        UUID invalidId = UUID.randomUUID();

        Assertions.assertThrows(VoucherDeleteFailException.class,()->service.deleteVoucher(invalidId));
    }

    @DisplayName("바우처 정보를 수정할 수 있어야 한다")
    @Test
    void voucherUpdateTest(){
        Voucher before = registerVoucher(VoucherType.PERCENT, 50);
        UUID id = before.getVoucherId();
        VoucherType otherType = VoucherType.FIXED;
        long otherAmount = 20000;
        VoucherUpdateForm form = new VoucherUpdateForm(before.getVoucherId(),otherType,otherAmount);

        service.updateVoucher(form);
        Voucher after = service.loadVoucherById(id);
        assertThat(after.getVoucherType()).isEqualTo(otherType);
        assertThat(after.getDiscountAmount()).isEqualTo(otherAmount);
    }

    private Voucher registerVoucher(Voucher.VoucherType type, long discountAmount) {
        VoucherCreateForm form = new VoucherCreateForm(type, discountAmount);
        return service.registerVoucher(form);
    }
}