package com.prgrms.controller;

import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.dto.VoucherRequest;
import com.prgrms.model.voucher.dto.VoucherResponse;
import com.prgrms.model.voucher.dto.discount.FixedDiscount;
import com.prgrms.model.voucher.dto.discount.PercentDiscount;
import com.prgrms.model.voucher.dto.mapper.DtoConverter;
import com.prgrms.service.voucher.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherControllerTest {

    @Mock
    private VoucherService voucherService;
    @Mock
    private DtoConverter dtoConverter;

    private VoucherController voucherController;

    private Voucher createdVoucher1;
    private Voucher createdVoucher2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        voucherController = new VoucherController(voucherService);
        int voucherId1 = 1;
        int voucherId2 = 2;

        createdVoucher1 = new FixedAmountVoucher(voucherId1, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        createdVoucher2 = new PercentDiscountVoucher(voucherId2, new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);
    }

    @Test
    @DisplayName("바우처 컨트롤러가 할인 정책에 맞는 바우처 정책을 생산하는지 확인한다.")
    public void createVoucher_CreatedVoucher_Equal() {
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(VoucherType.FIXED_AMOUNT_VOUCHER, new FixedDiscount(20));
        Mockito.when(voucherService.createVoucher(voucherRequest)).thenReturn(createdVoucher1);

        // When
        Voucher result = voucherController.createVoucher(voucherRequest);

        // Then
        assertThat(result).isEqualTo(createdVoucher1);
        Mockito.verify(voucherService, Mockito.times(1)).createVoucher(voucherRequest);
    }


    @Test
    @DisplayName("voucherId 가 다른 두 바우처 정책의 리스트를 잘 만드는지 테스트한다.")
    public void listVoucher_DifferentVoucherId_Equal() {
        // Given
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        Vouchers voucherRegistry = new Vouchers(list);

        List<VoucherResponse> expected = dtoConverter.convertVoucherResponse(voucherRegistry);
        Mockito.when(voucherService.getAllVoucherList()).thenReturn(expected);

        // When
        List<VoucherResponse> result = voucherController.listVoucher();

        // Then
        assertThat(result).isEqualTo(expected);
        Mockito.verify(voucherService, Mockito.times(1)).getAllVoucherList();
    }

    @Test
    @DisplayName("voucherId 가 같은 두 바우처 정책의 리스트를 잘 만드는지 테스트한다. 통과되므로 추후에 중복의 경우를 만들지 못하도록 예외로")
    public void listVoucher_SameVoucherId_Equal() {
        // given
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        Vouchers voucherRegistry = new Vouchers(list);

        List<VoucherResponse> expected = dtoConverter.convertVoucherResponse(voucherRegistry);
        Mockito.when(voucherService.getAllVoucherList()).thenReturn(expected);

        // when
        List<VoucherResponse> result = voucherController.listVoucher();

        // then
        assertThat(result).isEqualTo(expected);
        Mockito.verify(voucherService, Mockito.times(1)).getAllVoucherList();
    }
}
