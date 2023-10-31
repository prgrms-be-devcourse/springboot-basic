package com.prgrms.springbasic.domain.voucher.service;

import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.UpdateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    private VoucherService voucherService;
    @Mock
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    @DisplayName("바우처 생성 테스트")
    void testSaveVoucher() {
        CreateVoucherRequest request = new CreateVoucherRequest("percent", 50);
        Voucher expectedVoucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);

        when(voucherRepository.saveVoucher(any(Voucher.class))).thenReturn(expectedVoucher);
        VoucherResponse actualVoucher = voucherService.saveVoucher(request);

        verify(voucherRepository, times(1)).saveVoucher(any(Voucher.class));
        assertThat(expectedVoucher.getVoucherId()).isEqualTo(actualVoucher.voucherId());
        assertThat(expectedVoucher.getDiscountType()).isEqualTo(actualVoucher.discountType());
        assertThat(expectedVoucher.getDiscountValue()).isEqualTo(actualVoucher.discountValue());
    }

    @Test
    @DisplayName("모든 voucher 조회 테스트")
    void findAll() {
        List<Voucher> mockVouchers = new ArrayList<>();
        mockVouchers.add(Voucher.createVoucher(UUID.randomUUID(), "fixed", 10));
        mockVouchers.add(Voucher.createVoucher(UUID.randomUUID(), "percent", 10));

        when(voucherRepository.findAll()).thenReturn(mockVouchers);

        List<VoucherResponse> actualVouchers = voucherService.findAll();
        assertThat(actualVouchers).hasSize(2);
        verify(voucherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("voucher 업데이트 테스트")
    void updateVoucher() {
        UpdateVoucherRequest request = new UpdateVoucherRequest(UUID.randomUUID(), 50);
        Voucher mockVoucher = Voucher.createVoucher(request.voucherId(), "percent", 10);

        when(voucherRepository.findVoucherById(request.voucherId())).thenReturn(Optional.of(mockVoucher));
        voucherService.updateVoucher(request);

        verify(voucherRepository, times(1)).updateVoucher(mockVoucher);
        assertThat(mockVoucher.getDiscountValue()).isEqualTo(50);
    }

    @Test
    @DisplayName("모든 voucher 삭제 테스트")
    void deleteAll() {
        voucherService.deleteAll();
        verify(voucherRepository, times(1)).deleteAll();
    }
}
