package com.weeklyMission.voucher.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.PercentDiscountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class VoucherServiceTest {

    VoucherService voucherService;

    VoucherRepository voucherRepository = mock(VoucherRepository.class);

    @BeforeAll
    void init(){
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void createVoucherTest() {
        //given
        UUID id = UUID.randomUUID();

        //when
        when(voucherRepository.save(any(Voucher.class)))
            .thenReturn(new FixedAmountVoucher(id, 10));
        VoucherResponse result = voucherService.createVoucher(new VoucherRequest("fixed", id, 10));

        //then
        assertThat(result.voucherId()).isEqualTo(id);
        verify(voucherRepository, times(1)).save(any(Voucher.class));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void getVoucherListTest() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        List<Voucher> result = List.of(fixedAmountVoucher, percentDiscountVoucher);

        //when
        when(voucherRepository.findAll()).thenReturn(result);

        //then
        List<VoucherResponse> voucherList = voucherService.getVoucherList();
        assertThat(voucherList.get(0).voucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
        verify(voucherRepository, times(1)).findAll();
    }
}