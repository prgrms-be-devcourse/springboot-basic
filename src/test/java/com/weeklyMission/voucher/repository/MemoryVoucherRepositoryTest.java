package com.weeklyMission.voucher.repository;

import static org.assertj.core.api.Assertions.*;

import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.PercentDiscountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    void init(){
        memoryVoucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("도서 저장 테스트")
    void testCreate(){
        //given
        Voucher createVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1);

        //when
        Voucher getVoucher = memoryVoucherRepository.save(createVoucher);

        //then
        assertThat(getVoucher).isEqualTo(createVoucher);
    }

    @Test
    @DisplayName("도서 전체 조회 테스트")
    void testGetList() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);

        //when
        memoryVoucherRepository.save(fixedAmountVoucher);
        memoryVoucherRepository.save(percentDiscountVoucher);

        //then
        assertThat(memoryVoucherRepository.findAll().size()).isEqualTo(2);
    }
}