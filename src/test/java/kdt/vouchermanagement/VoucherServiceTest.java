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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("바우처 삭제시 바우처가 존재하지 않으면 IllegalArgumentException이 발생한다.")
    void notExistVoucherWhenDeleteVoucher() {
        //given
        Long voucherId = 1L;
        doReturn(Optional.empty()).when(voucherRepository).findById(any());

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherService.deleteVoucher(voucherId));
    }

    @Test
    @DisplayName("바우처 삭제시 바우처가 존재하면 repository에게 바우처 삭제를 요청한다.")
    void requestDeleteVoucher() {
        //given
        Long voucherId = 1L;
        Voucher voucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 10, LocalDateTime.now(), LocalDateTime.now());
        doReturn(Optional.of(voucher)).when(voucherRepository).findById(any());

        //when
        voucherService.deleteVoucher(voucherId);

        //then
        verify(voucherRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("바우처 id 값으로 조회시 바우처가 존재하지 않으면 IllegalArgumentException이 발생한다.")
    void notExistVoucherWhenFindVoucherById() {
        //given
        Long voucherId = 1L;
        doReturn(Optional.empty()).when(voucherRepository).findById(any());

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherService.findVoucher(voucherId));
    }

    @Test
    @DisplayName("바우처 목록 조회 요청에 대한 반환값인 바우처 리스트 반환_성공")
    void responseFindVouchers() {
        //given
        Voucher firstVoucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher secondVoucher = new FixedAmountVoucher(2L, VoucherType.FIXED_AMOUNT, 200, LocalDateTime.now(), LocalDateTime.now());
        List<Voucher> vouchers = List.of(firstVoucher, secondVoucher);
        doReturn(vouchers).when(voucherRepository).findAll();

        //when
        List<Voucher> foundVouchers = voucherService.findVouchers();

        //then
        assertThat(foundVouchers).containsOnly(firstVoucher, secondVoucher);
    }

    @Test
    @DisplayName("모든 바우처 목록 조회 요청이 들어올 때 모든 바우처를 담은 리스트를 반환한다")
    void returnVouchersWhenRequestAllVoucher() {
        //given
        Voucher firstVoucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher secondVoucher = new FixedAmountVoucher(2L, VoucherType.FIXED_AMOUNT, 200, LocalDateTime.now(), LocalDateTime.now());
        List<Voucher> vouchers = List.of(firstVoucher, secondVoucher);
        doReturn(vouchers).when(voucherRepository).findAll();

        //when
        List<Voucher> foundVouchers = voucherService.findVouchers();

        //then
        assertThat(foundVouchers).containsOnly(firstVoucher, secondVoucher);
    }

    @Test
    @DisplayName("바우처 타입과 생성날짜별 조회 요청이 들어올 때 필터링된 바우처 목록을 반환한다")
    void returnVouchersWhenRequestVoucherByTypeAndDate() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        LocalDate date = LocalDate.of(2022, 5, 9);
        Voucher voucher = new FixedAmountVoucher(1L, type, 100, LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MIN));
        List<Voucher> vouchers = List.of(voucher);
        doReturn(vouchers).when(voucherRepository).findByTypeAndDate(any(VoucherType.class), any(LocalDate.class));

        //when
        List<Voucher> foundVouchers = voucherService.findVouchersByTypeAndDate(VoucherType.FIXED_AMOUNT, date);

        //then
        assertThat(foundVouchers).containsOnly(voucher);
    }

    @Test
    @DisplayName("바우처 타입별 조회 요청이 들어올 때 필터링된 바우처 목록을 반환한다")
    void returnVouchersWhenRequestVoucherByType() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        Voucher voucher = new FixedAmountVoucher(
                1L,
                type,
                100,
                LocalDateTime.of(2022, 5, 9, 00, 00,00,0000),
                LocalDateTime.of(2022, 5, 9, 00, 00,00,0000));
        List<Voucher> vouchers = List.of(voucher);
        doReturn(vouchers).when(voucherRepository).findByType(any(VoucherType.class));

        //when
        List<Voucher> foundVouchers = voucherService.findVouchersByType(type);

        //then
        assertThat(foundVouchers).containsOnly(voucher);
    }

    @Test
    @DisplayName("바우처 생성날짜별 조회 요청이 들어올 때 필터링된 바우처 목록을 반환한다")
    void returnVouchersWhenRequestVoucherByDate() {
        //given
        LocalDate date = LocalDate.of(2022, 5, 9);
        Voucher voucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 100, LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MIN));
        List<Voucher> vouchers = List.of(voucher);
        doReturn(vouchers).when(voucherRepository).findByDate(any(LocalDate.class));

        //when
        List<Voucher> foundVouchers = voucherService.findVouchersByDate(date);

        //then
        assertThat(foundVouchers).containsOnly(voucher);
    }
}