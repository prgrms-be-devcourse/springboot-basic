package org.prgms.kdtspringweek1.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    void init() {
        memoryVoucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("바우처 저장 성공")
    void Success_Save() {
        // given
        Voucher voucher = FixedAmountVoucher.createWithAmount(7);
        int beforeSave = memoryVoucherRepository.findAllVouchers().size();

        // when
        Voucher savedVoucher = memoryVoucherRepository.save(voucher);

        // then
        assertThat(savedVoucher, samePropertyValuesAs(voucher));
        assertThat(memoryVoucherRepository.findAllVouchers(), hasSize(beforeSave + 1));
    }

    @Test
    @DisplayName("모든 바우처 조회 성공")
    void Success_FindAllVouchers() {
        // given
        List<Voucher> vouchers = saveVouchers();

        // when
        List<Voucher> allVouchers = memoryVoucherRepository.findAllVouchers();

        // then
        assertThat(vouchers, containsInAnyOrder(vouchers.toArray()));
    }

    private List<Voucher> saveVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            memoryVoucherRepository.save(FixedAmountVoucher.createWithAmount(7));
            memoryVoucherRepository.save(PercentDiscountVoucher.createWithPercent(7));
        }

        return vouchers;
    }
}