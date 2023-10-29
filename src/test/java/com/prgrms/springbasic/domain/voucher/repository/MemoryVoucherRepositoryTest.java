package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    private VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void testSaveVoucher() {
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);

        Voucher savedVoucher = voucherRepository.saveVoucher(voucher);
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(savedVoucher).isEqualTo(voucher);
        assertThat(vouchers).hasSize(1).containsExactly(savedVoucher);
    }

    @Test
    @DisplayName("바우처 값 업데이트 기능 테스트")
    void testUpdateVoucher() {
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);
        voucherRepository.saveVoucher(voucher);

        Voucher updateVoucher = Voucher.createVoucher(voucher.getVoucherId(), voucher.getDiscountType().toString(), 80);
        voucherRepository.updateVoucher(updateVoucher);

        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(1).containsExactly(updateVoucher);
        assertThat(vouchers.get(0).getDiscountValue()).isEqualTo(80);
    }

    @Test
    @DisplayName("바우처 전체 삭제 기능 테스트")
    void testDeleteAll() {
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);
        voucherRepository.saveVoucher(voucher);

        voucherRepository.deleteAll();

        assertThat(voucherRepository.findAll()).hasSize(0);
    }

    @Test
    void findVoucherById() {
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);
        voucherRepository.saveVoucher(voucher);
        Voucher foundVoucher = voucherRepository.findVoucherById(voucher.getVoucherId())
                .orElseThrow();

        assertThat(voucher).isEqualTo(foundVoucher);
    }
}
