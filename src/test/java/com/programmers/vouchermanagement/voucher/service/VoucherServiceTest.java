package com.programmers.vouchermanagement.voucher.service;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
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
    Voucher mockVoucher;

    @Test
    @DisplayName("단위 테스트를 위해 Service에 Mock 객체(Repository) 주입")
    void initTest() {
        assertThat(voucherRepository).isNotNull();
        assertThat(voucherService).isNotNull();
    }

    @Test
    @DisplayName("고정 할인 금액 바우처 객체를 생성할 수 있다.")
    void createFixedAmountVoucherSucceed() {
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(BigDecimal.valueOf(1000), VoucherType.FIXED);
        voucherService.createVoucher(createVoucherRequest);

        verify(voucherRepository, times(1)).save(any(Voucher.class));
    }

    @Test
    @DisplayName("퍼센트 할인 바우처 객체를 생성할 수 있다.")
    void createPercentDiscountVoucherSucceed() {
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(BigDecimal.valueOf(100), VoucherType.PERCENT);
        voucherService.createVoucher(createVoucherRequest);

        verify(voucherRepository, times(1)).save(any(Voucher.class));
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void readAllVouchersSucceed() {
        voucherService.readAllVouchers();

        when(voucherRepository.findAll()).thenReturn(new ArrayList<>());

        verify(voucherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("바우처를 id로 조회할 수 있다.")
    void readVoucherByIdSucceed() {
        voucherService.readVoucherById(mockId);

        when(voucherRepository.findById(mockId)).thenReturn(Optional.of(mockVoucher));

        verify(voucherRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("바우처를 id로 삭제할 수 있다.")
    void deleteVoucherSucceed() {
        voucherService.deleteVoucher(mockId);

        verify(voucherRepository, times(1)).delete(any(UUID.class));
    }

    @Test
    @DisplayName("바우처를 업데이트할 수 있다.")
    void updateVoucherSucceed() {
        voucherService.updateVoucher(new Voucher(UUID.randomUUID(), BigDecimal.valueOf(100), VoucherType.FIXED));

        verify(voucherRepository, times(1)).update(any(Voucher.class));
    }
}