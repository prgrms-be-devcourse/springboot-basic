package com.programmers.vouchermanagement.voucher.service;

import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class VoucherServiceTest {
    @InjectMocks
    VoucherService voucherService;
    @Mock
    VoucherRepository voucherRepository;
    @Mock
    UUID mockId;
    @Mock
    LocalDateTime mockLocalDateTime;
    @Mock
    Voucher mockVoucher;

    @Test
    @DisplayName("단위 테스트를 위해 Service에 Mock 객체(Repository) 주입")
    void initTest() {
        assertThat(voucherRepository).isNotNull();
        assertThat(voucherService).isNotNull();
    }

    @Test
    @DisplayName("고정 할인 금액 바우처 객체를 생성할 수 있다.")
    void createFixedAmountVoucher() {
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest("FIXED", 1000);
        voucherService.create(createVoucherRequest);

        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    @DisplayName("퍼센트 할인 바우처 객체를 생성할 수 있다.")
    void createPercentDiscountVoucher() {
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest("PERCENT", 100);
        voucherService.create(createVoucherRequest);

        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void readAllVouchers() {
        voucherService.readAll();

        when(voucherRepository.findAll()).thenReturn(new ArrayList<>());

        verify(voucherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("바우처를 id로 조회할 수 있다.")
    void readVoucherById() {
        when(voucherRepository.findById(mockId)).thenReturn(Optional.of(mockVoucher));

        voucherService.readById(mockId);

        verify(voucherRepository, times(1)).findById(mockId);
    }

    @Test
    @DisplayName("바우처를 id로 삭제할 수 있다.")
    void deleteVoucher() {
        when(voucherRepository.findById(mockId)).thenReturn(Optional.of(mockVoucher));

        voucherService.delete(mockId);

        verify(voucherRepository, times(1)).delete(any(UUID.class));
    }

    @Test
    @DisplayName("바우처를 업데이트할 수 있다.")
    void updateVoucher() {
        when(voucherRepository.findById(mockId)).thenReturn(Optional.of(new Voucher(mockId, mockLocalDateTime, "FIXED", 130)));

        voucherService.update(mockId, new CreateVoucherRequest("FIXED", 100));

        verify(voucherRepository, times(1)).update(any(Voucher.class));
    }
}