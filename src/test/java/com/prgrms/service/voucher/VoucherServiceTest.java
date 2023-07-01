package com.prgrms.service.voucher;

import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.voucher.*;
import com.prgrms.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private VoucherCreator voucherCreator;

    private VoucherService voucherService;
    private UUID voucherId1;
    private UUID voucherId2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherService(voucherRepository, voucherCreator);
        voucherId1 = UUID.randomUUID();
        voucherId2 = UUID.randomUUID();

    }

    @Test
    public void testCreateVoucherCreatedVoucher() {
        VoucherRequest voucherRequest = new VoucherRequest(VoucherPolicy.FixedAmountVoucher, new Discount(10));
        Voucher createdVoucher = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherPolicy.FixedAmountVoucher);
        ;

        when(voucherCreator.createVoucher(any(Discount.class), any(VoucherPolicy.class)))
                .thenReturn(createdVoucher);
        when(voucherRepository.insert(any(Voucher.class))).thenReturn(createdVoucher);

        Voucher result = voucherService.createVoucher(voucherRequest);

        assertNotNull(result);
        assertEquals(createdVoucher, result);
        verify(voucherCreator, times(1)).createVoucher(any(Discount.class), any(VoucherPolicy.class));
        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    public void testGetAllVoucherListVoucherList() {
        Voucher createdVoucher1 = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherPolicy.FixedAmountVoucher);
        Voucher createdVoucher2 = new PercentDiscountVoucher(voucherId2, new Discount(20), VoucherPolicy.PercentDiscountVoucher);
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        VoucherList voucherList = new VoucherList(list);

        when(voucherRepository.getAllVoucherList()).thenReturn(voucherList);

        VoucherList result = voucherService.getAllVoucherList();

        assertNotNull(result);
        assertEquals(voucherList, result);
        verify(voucherRepository, times(1)).getAllVoucherList();
    }
}