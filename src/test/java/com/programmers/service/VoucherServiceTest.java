package com.programmers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.programmers.domain.voucher.FixedAmountVoucher;
import com.programmers.domain.voucher.Voucher;
import com.programmers.repository.voucher.MemoryVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@ActiveProfiles("local")
class VoucherServiceTest {

    @Mock
    MemoryVoucherRepository memoryVoucherRepository;

    @DisplayName("바우처를 저장하고 조회한다")
    @Test
    void saveAndFindAll() {
        //given
        MockitoAnnotations.openMocks(this);

        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(uuid, "voucherName1", 10L);
        List<Voucher> vouchers = List.of(fixedAmountVoucher);

        when(memoryVoucherRepository.save(fixedAmountVoucher)).thenReturn(fixedAmountVoucher);
        when(memoryVoucherRepository.findAll()).thenReturn(vouchers);

        VoucherService voucherService = new VoucherService(memoryVoucherRepository);

        //when
        voucherService.save(fixedAmountVoucher);
        List<Voucher> result = voucherService.findAll();

        //then
        assertThat(result.get(0).getVoucherId()).isEqualTo(uuid);
    }
}