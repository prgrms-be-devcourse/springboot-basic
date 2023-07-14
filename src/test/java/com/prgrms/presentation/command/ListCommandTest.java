package com.prgrms.presentation.command;

import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.PercentDiscountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import com.prgrms.presentation.view.ViewManager;
import com.prgrms.service.voucher.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

class ListCommandTest {

    private final int FIX_VOUCHER_ID = 1;
    private final int PERCENT_VOUCHER_ID = 1;
    private final Discount FIX_DISCOUNT = new FixedDiscount(20);
    private final Discount PERCENT_DISCOUNT = new PercentDiscount(20);

    @Mock
    private VoucherService voucherService;
    @Mock
    private ViewManager viewManager;
    private ListCommand listCommand;
    private Voucher fixedVoucher;
    private Voucher percentVoucher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        listCommand = new ListCommand(voucherService);

        fixedVoucher = new FixedAmountVoucher(FIX_VOUCHER_ID, FIX_DISCOUNT, VoucherType.FIXED_AMOUNT_VOUCHER);
        percentVoucher = new PercentDiscountVoucher(PERCENT_VOUCHER_ID, PERCENT_DISCOUNT, VoucherType.FIXED_AMOUNT_VOUCHER);
    }

    @Test
    public void testExecute() {
        //given
        List<VoucherResponse> vouchers = new ArrayList<>();
        vouchers.add(new VoucherResponse(fixedVoucher));
        vouchers.add(new VoucherResponse(percentVoucher));
        Mockito.when(voucherService.getAllVoucherList()).thenReturn(vouchers);

        //when
        listCommand.execute(viewManager);

        //then
        Mockito.verify(viewManager, times(1)).viewVoucherList(vouchers);
    }
}
