package org.prgrms.kdt.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 9:53 오전
 */
@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private InMemoryVoucherRepository inMemoryVoucherRepository;

    @Test
    @DisplayName("FixedAmountVoucher 저장 테스트")
    void createFixedVoucher() {
        // given
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher mockVoucher = new FixedAmountVoucher(voucherId, 100L);
        given(inMemoryVoucherRepository.insert(mockVoucher)).willReturn(mockVoucher);

        // when
        var voucher = voucherService.createVoucher(mockVoucher);

        // then
        verify(inMemoryVoucherRepository, times(1)).insert(mockVoucher);
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);

    }

    @Test
    @DisplayName("PercentDiscountVoucher 저장 테스트")
    void createPercentVoucher() {
        // given
        UUID voucherId = UUID.randomUUID();
        PercentDiscountVoucher mockVoucher = new PercentDiscountVoucher(voucherId, 30L);
        given(inMemoryVoucherRepository.insert(mockVoucher)).willReturn(mockVoucher);

        // when
        var voucher = voucherService.createVoucher(mockVoucher);

        // then
        verify(inMemoryVoucherRepository, times(1)).insert(mockVoucher);
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);

    }

    @Test
    @DisplayName("FixedAmountVoucher 전체 조회 테스트")
    void getAllFixedAmountVoucher() {
        // given
        var voucherId1 = UUID.randomUUID();
        var voucherId2 = UUID.randomUUID();
        var voucherId3 = UUID.randomUUID();
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(voucherId1, 10L);
        FixedAmountVoucher voucher2 = new FixedAmountVoucher(voucherId2, 40L);
        FixedAmountVoucher voucher3 = new FixedAmountVoucher(voucherId3, 900L);

        given(inMemoryVoucherRepository.findAll()).willReturn(Map.of(
                voucherId1, voucher1,
                voucherId2, voucher2,
                voucherId3, voucher3));

        // when
        var vouchers = voucherService.getAllVoucher();

        // then
        verify(inMemoryVoucherRepository, times(1)).findAll();
        assertAll(
                () -> assertThat(vouchers.size()).isEqualTo(3),
                () -> assertThat(vouchers.get(voucherId1)).isEqualTo(voucher1),
                () -> assertThat(vouchers.get(voucherId2)).isEqualTo(voucher2),
                () -> assertThat(vouchers.get(voucherId3)).isEqualTo(voucher3)
        );
    }

    @Test
    @DisplayName("PercentDiscountVoucher 전체 조회 테스트")
    void getAllPercentDiscountVoucher() {

        // given
        var voucherId1 = UUID.randomUUID();
        var voucherId2 = UUID.randomUUID();
        var voucherId3 = UUID.randomUUID();
        PercentDiscountVoucher voucher1 = new PercentDiscountVoucher(voucherId1, 10L);
        PercentDiscountVoucher voucher2 = new PercentDiscountVoucher(voucherId2, 40L);
        PercentDiscountVoucher voucher3 = new PercentDiscountVoucher(voucherId3, 80L);

        given(inMemoryVoucherRepository.findAll()).willReturn(Map.of(
                voucherId1, voucher1,
                voucherId2, voucher2,
                voucherId3, voucher3));

        // when
        Map<UUID, Voucher> vouchers = voucherService.getAllVoucher();

        // then
        verify(inMemoryVoucherRepository, times(1)).findAll();
        assertAll(
                () -> assertThat(vouchers.size()).isEqualTo(3),
                () -> assertThat(vouchers.get(voucherId1)).isEqualTo(voucher1),
                () -> assertThat(vouchers.get(voucherId2)).isEqualTo(voucher2),
                () -> assertThat(vouchers.get(voucherId3)).isEqualTo(voucher3)
        );
    }

}