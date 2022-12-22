package org.prgrms.java.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.notfound.VoucherNotFoundException;
import org.prgrms.java.repository.voucher.VoucherRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {
    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("서비스를 통해 바우처를 등록할 수 있다.")
    void testCreateVoucher() {
        Voucher voucher = createFixedAmountVoucher(UUID.randomUUID());
        when(voucherRepository.insert(any())).thenReturn(voucher);

        Voucher insertedVoucher = voucherService.saveVoucher(null, voucher.getType().toString(), voucher.getAmount(), voucher.getExpiredAt());

        assertThat(insertedVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("서비스를 통해 바우처를 조회할 수 있다.")
    void testGetVoucher() {
        Voucher voucher = createFixedAmountVoucher(UUID.randomUUID());
        when(voucherRepository.findById(any())).thenReturn(Optional.of(voucher));

        Voucher fixedAmountVoucher = voucherService.getVoucherById(voucher.getVoucherId().toString());

        assertThat(fixedAmountVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("존재하지 않는 바우처를 조회하면 예외가 발생한다.")
    void testGetNonExistVoucher() {
        when(voucherRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(VoucherNotFoundException.class, () -> voucherService.getVoucherById(UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("바우처를 등록하지 않으면 전체 조회시 빈 컬렉션이 반환된다.")
    void testGetAllVoucherWithNoCreation() {
        when(voucherRepository.findAll()).thenReturn(Collections.emptyList());

        assertThat(voucherService.getAllVouchers(), hasSize(0));
    }

    @Test
    @DisplayName("서비스를 통해 모든 바우처를 조회할 수 있다.")
    void testGetAllVouchers() {
        Voucher fixedAmountVoucher = createFixedAmountVoucher(UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(UUID.randomUUID());

        when(voucherRepository.findAll()).thenReturn(List.of(fixedAmountVoucher, percentDiscountVoucher));

        assertThat(voucherService.getAllVouchers(), hasSize(2));
        assertThat(voucherService.getAllVouchers(), containsInAnyOrder(samePropertyValuesAs(fixedAmountVoucher), samePropertyValuesAs(percentDiscountVoucher)));
    }

    private Voucher createFixedAmountVoucher(UUID voucherId) {
        return FixedAmountVoucher.builder()
                .voucherId((voucherId != null) ? voucherId : UUID.randomUUID())
                .amount(1000)
                .isUsed(false)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now())
                .build();
    }

    private Voucher createPercentDiscountVoucher(UUID voucherId) {
        return PercentDiscountVoucher.builder()
                .voucherId((voucherId != null) ? voucherId : UUID.randomUUID())
                .amount(50)
                .isUsed(false)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now())
                .build();
    }
}
