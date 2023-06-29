package com.prgrms.controller;

import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import com.prgrms.service.voucher.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoucherControllerTest {

    @Mock
    private VoucherService voucherService;

    private VoucherController voucherController;
    private UUID voucherId1;
    private UUID voucherId2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        voucherController = new VoucherController(voucherService);
        voucherId1 = UUID.randomUUID();
        voucherId2 = UUID.randomUUID();

    }

    @Test
    public void testCreateVoucher() {
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(VoucherPolicy.FixedAmountVoucher, new Discount(20));
        Voucher createdVoucher = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherPolicy.FixedAmountVoucher);
        Mockito.when(voucherService.createVoucher(voucherRequest)).thenReturn(createdVoucher);

        // When
        Voucher result = voucherController.createVoucher(voucherRequest);

        // Then
        Assertions.assertEquals(createdVoucher, result);
        Mockito.verify(voucherService, Mockito.times(1)).createVoucher(voucherRequest);
    }


    @Test
    @DisplayName("voucherId 가 다른 두 바우처 정책의 리스트를 잘 만드는지 테스트합니다.")
    public void testListVoucher_DifferentVoucherId() {
        // Given
        Voucher createdVoucher1 = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherPolicy.FixedAmountVoucher);
        Voucher createdVoucher2 = new PercentDiscountVoucher(voucherId2, new Discount(20), VoucherPolicy.PercentDiscountVoucher);
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        VoucherList voucherList = new VoucherList(list);
        Mockito.when(voucherService.getAllVoucherList()).thenReturn(voucherList);

        // When
        VoucherList result = voucherController.listVoucher();

        // Then
        Assertions.assertEquals(voucherList, result);
        Mockito.verify(voucherService, Mockito.times(1)).getAllVoucherList();
    }

    @Test
    @DisplayName("voucherId 가 같은 두 바우처 정책의 리스트를 잘 만드는지 테스트합니다. 통과되므로 추후에 중복의 경우를 만들지 못하도록 예외로")
    public void testListVoucher_SameVoucherId() {
        // Given
        Voucher createdVoucher1 = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherPolicy.FixedAmountVoucher);
        Voucher createdVoucher2 = new PercentDiscountVoucher(voucherId1, new Discount(20), VoucherPolicy.PercentDiscountVoucher);
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        VoucherList voucherList = new VoucherList(list);
        Mockito.when(voucherService.getAllVoucherList()).thenReturn(voucherList);
        // When
        VoucherList result = voucherController.listVoucher();
        // Then
        Assertions.assertEquals(voucherList, result);
        Mockito.verify(voucherService, Mockito.times(1)).getAllVoucherList();
    }
}