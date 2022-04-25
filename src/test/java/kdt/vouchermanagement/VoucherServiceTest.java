package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.PercentDiscountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.repository.VoucherRepository;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import kdt.vouchermanagement.domain.voucher.service.VoucherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherServiceImpl(voucherRepository);
    }

    @Test
    @DisplayName("FixedAmountVoucher 타입의 할인값이 음수면_실패")
    void negativeDiscountValueWhenFixedAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;
        int discountValue = -1;
        Voucher voucher = new FixedAmountVoucher(voucherType, discountValue);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(voucher));
    }

    @Test
    @DisplayName("FixedAmountVoucher 타입의 할인값 0이면_실패")
    void zeroDiscountValueWhenFixedAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;
        int discountValue = 0;
        Voucher voucher = new FixedAmountVoucher(voucherType, discountValue);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(voucher));
    }

    @Test
    @DisplayName("전달받은 Voucher가 NULL이라면 FixedAmountVoucher 생성_실패")
    void notCreateFixedAmountVoucher() {
        //given
        Voucher voucher = null;

        //when, then
        verify(voucherRepository, times(0)).save(any());
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(voucher));
    }

    @Test
    @DisplayName("FixedAmountVoucher 생성_성공")
    void createFixedAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;
        int discountValue = 100;
        Voucher voucher = new FixedAmountVoucher(voucherType, discountValue);

        doReturn(voucher).when(voucherRepository).save(any(Voucher.class));

        //when
        Voucher createdVoucher = voucherService.createVoucher(voucher);

        //then
        verify(voucherRepository, times(1)).save(any());
        assertThat(createdVoucher).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 목록 조회 요청에 대한 반환값인 바우처 리스트 반환_성공")
    void responseFindVouchers() {
        //given
        Voucher firstVoucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 100);
        Voucher secondVoucher = new FixedAmountVoucher(2L, VoucherType.FIXED_AMOUNT, 200);
        List<Voucher> vouchers = List.of(firstVoucher, secondVoucher);

        //when
        doReturn(vouchers).when(voucherRepository).findAll();
        List<Voucher> foundVouchers = voucherService.findVouchers();

        //then
        assertThat(foundVouchers).containsOnly(firstVoucher, secondVoucher);
    }

    @Test
    @DisplayName("PercentAmountVoucher 타입의 할인값이 음수면 IllegalArgumentException이 발생한다_실패")
    void negativeDiscountValueWhenPercentAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
        int discountValue = -1;
        Voucher voucher = new PercentDiscountVoucher(voucherType, discountValue);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(voucher));
    }

    @Test
    @DisplayName("PercentAmountVoucher 타입의 할인값 0이면 IllegalArgumentException이 발생한다_실패")
    void zeroDiscountValueWhenPercentAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
        int discountValue = 0;
        Voucher voucher = new PercentDiscountVoucher(voucherType, discountValue);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(voucher));
    }

    @Test
    @DisplayName("PercentAmountVoucher 타입의 할인값 100 이상이면 IllegalArgumentException이 발생한다_실패")
    void over100DiscountValueWhenPercentAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
        int discountValue = 1000;
        Voucher voucher = new PercentDiscountVoucher(voucherType, discountValue);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(voucher));
    }

    @Test
    @DisplayName("PercentAmountVoucher 생성_성공")
    void createPercentAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
        int discountValue = 100;
        Voucher voucher = new PercentDiscountVoucher(voucherType, discountValue);

        doReturn(voucher).when(voucherRepository).save(any(Voucher.class));

        //when
        Voucher createdVoucher = voucherService.createVoucher(voucher);

        //then
        verify(voucherRepository, times(1)).save(any());
        assertThat(createdVoucher).usingRecursiveComparison().isEqualTo(voucher);
    }
}