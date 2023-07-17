package com.example.voucher.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.PercentDiscountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.service.voucher.dto.VoucherDTO;
import com.example.voucher.repository.voucher.VoucherRepository;
import com.example.voucher.service.voucher.VoucherService;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    @DisplayName("정상적인 바우처 타입과, 할인정보를 받았을 때 createVoucher 메서드를 수행하면 바우처 저장이 수행되고 저당된 바우처 정보의 DTO가 반환된다")
    @ParameterizedTest
    @MethodSource("voucherData")
    void createVoucher(UUID voucherId, VoucherType voucherType, long discountValue) {
        Voucher voucher = voucherType.createVoucher(voucherId, discountValue);
        when(voucherRepository.save(any(Voucher.class))).thenReturn(voucher);

        VoucherDTO result = voucherService.createVoucher(voucherType, discountValue);

        verify(voucherRepository, times(1)).save(any(Voucher.class));
        assertEquals(voucher.getVoucherId(), result.voucherId());
    }

    @DisplayName("고정 금액 할인 바우처에서 할인값이 음수일 때 createVoucher 메서드를 수행하면 IllegalArgumentException 발생한다.")
    @Test
    void createFixedAmountVoucherWithIllegalArg() {
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_DISCOUNT;
        long discountValue = -1L;

        assertThrows(IllegalArgumentException.class,
            () -> voucherService.createVoucher(voucherType, discountValue));
    }

    @DisplayName("퍼센트 할인 바우처에서 할인값이 0미만 100 초과일 때 createVoucher 메서드를 수행하면 IllegalArgumentException 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 101})
    void createPercentDiscountVoucherWithIllegalArg(long discountValue) {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;

        assertThrows(IllegalArgumentException.class,
            () -> voucherService.createVoucher(voucherType, discountValue));
    }

    @DisplayName("바우처 아이디를 입력해 searchById 메서드를 수행하면 바우처 조회가 수행되고 id를 식별자로 같은 바우처 정보의 DTO가 반환된다.")
    @ParameterizedTest
    @MethodSource("voucherData")
    void search(UUID voucherId, VoucherType voucherType, long discountValue) {
        Voucher voucher = voucherType.createVoucher(voucherId, discountValue);
        when(voucherRepository.findById(voucherId)).thenReturn(voucher);

        VoucherDTO result = voucherService.search(voucherId);

        verify(voucherRepository, times(1)).findById(voucherId);
        assertEquals(voucher.getVoucherId(), result.voucherId());
    }

    @DisplayName("두 개의 데이터가 있을 때 getVouchers 메서드를 수행하면 바우처 전체조회가 수행되고 크기가 2인 바우처 정보의 DTO가 반환된다.")
    @Test
    void getVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(new FixedAmountVoucher(10L));
        vouchers.add(new PercentDiscountVoucher(20L));
        when(voucherRepository.findAll()).thenReturn(vouchers);

        List<VoucherDTO> result = voucherService.getVouchers();

        verify(voucherRepository, times(1)).findAll();
        assertEquals(2, result.size());
    }

    @DisplayName("deleteVoucher 매서드를 수행하면 아디를 조건으로 갖는 바우처 삭제가 수행된다.")
    @Test
    void deleteVoucher() {
        UUID voucherId = UUID.randomUUID();

        voucherService.deleteVoucher(voucherId);

        verify(voucherRepository, times(1)).deleteById(voucherId);
    }

    @DisplayName("deleteVouchers 매서드를 수행하면 바우처 전체 삭제가 수행된다.")
    @Test
    void deleteVouchers() {
        voucherService.deleteVouchers();

        verify(voucherRepository, times(1)).deleteAll();
    }

    @DisplayName("수정할 바우처 정보가 주어졌을 때 update 메서드를 수행하면 바우처 수정이 수행되고 수정된 바우처 정보의 DTO가 반환된다.")
    @ParameterizedTest
    @MethodSource("voucherData")
    void update(UUID voucherId, VoucherType voucherType, long discountValue) {
        Voucher voucher = voucherType.createVoucher(voucherId, discountValue);
        when(voucherRepository.update(any(Voucher.class))).thenReturn(voucher);

        VoucherDTO result = voucherService.update(voucherId, voucherType, discountValue);

        verify(voucherRepository, times(1)).update(voucher);
        assertEquals(voucher.getVoucherId(), result.voucherId());
        assertEquals(voucher.getVoucherType(), result.voucherType());
        assertEquals(voucher.getValue(), result.value());
    }

    private static Stream<Arguments> voucherData() {
        return Stream.of(
            Arguments.of(UUID.randomUUID(), VoucherType.FIXED_AMOUNT_DISCOUNT, 10L),
            Arguments.of(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 20L)
        );
    }

}