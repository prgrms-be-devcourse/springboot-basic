package com.prgrms.controller;

import com.prgrms.model.dto.mapper.DtoConverter;
import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import com.prgrms.service.voucher.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherControllerTest {

    @Mock
    private VoucherService voucherService;

    private VoucherController voucherController;

    private Voucher createdVoucher1;
    private Voucher createdVoucher2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        voucherController = new VoucherController(voucherService);

        createdVoucher1 = new FixedAmountVoucher(new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        createdVoucher2 = new PercentDiscountVoucher(new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);


    }

    @Test
    @DisplayName("값 누락 없이 바우처 정책을 잘 만드는지 테스트한다.")
    public void CreateVoucher_CreatedVoucher_Equal() {
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(VoucherType.FIXED_AMOUNT_VOUCHER, new FixedDiscount(20));
        Voucher createdVoucher = new FixedAmountVoucher(new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Mockito.when(voucherService.createVoucher(voucherRequest)).thenReturn(createdVoucher);

        // When
        Voucher result = voucherController.createVoucher(voucherRequest);

        // Then
        assertThat(result).isEqualTo(createdVoucher);
        Mockito.verify(voucherService, Mockito.times(1)).createVoucher(voucherRequest);
    }


    @Test
    @DisplayName("voucherId 가 다른 두 바우처 정책의 리스트를 잘 만드는지 테스트합니다.")
    public void ListVoucher_DifferentVoucherId_Equal() {
        // Given
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        VoucherRegistry voucherRegistry = new VoucherRegistry(list);

        DtoConverter dtoConverter = new DtoConverter();
        List<VoucherResponse> expected = dtoConverter.convertVoucherResponse(voucherRegistry);
        Mockito.when(voucherService.getAllVoucherList()).thenReturn(expected);

        // When
        List<VoucherResponse> result = voucherController.listVoucher();

        // Then
        assertThat(result).isEqualTo(expected);
        Mockito.verify(voucherService, Mockito.times(1)).getAllVoucherList();
    }

    @Test
    @DisplayName("voucherId 가 같은 두 바우처 정책의 리스트를 잘 만드는지 테스트합니다. 통과되므로 추후에 중복의 경우를 만들지 못하도록 예외로")
    public void ListVoucher_SameVoucherId_Equal() {
        // Given
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        VoucherRegistry voucherRegistry = new VoucherRegistry(list);

        DtoConverter dtoConverter = new DtoConverter();
        List<VoucherResponse> expected = dtoConverter.convertVoucherResponse(voucherRegistry);
        Mockito.when(voucherService.getAllVoucherList()).thenReturn(expected);

        // When
        List<VoucherResponse> result = voucherController.listVoucher();

        // Then
        assertThat(result).isEqualTo(expected);
        Mockito.verify(voucherService, Mockito.times(1)).getAllVoucherList();
    }
}
