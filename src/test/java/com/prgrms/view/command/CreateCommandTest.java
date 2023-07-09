package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.dto.VoucherRequest;
import com.prgrms.model.voucher.dto.discount.PercentDiscount;
import com.prgrms.view.ViewManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateCommandTest {
    @Mock
    private VoucherController voucherController;
    @Mock
    private ViewManager viewManager;

    @Test
    void execute_On_PowerON() {
        //given
        MockitoAnnotations.openMocks(this);
        CreateCommand createCommand = new CreateCommand(voucherController, viewManager);

        VoucherRequest voucherRequest = new VoucherRequest(VoucherType.PERCENT_DISCOUNT_VOUCHER, new PercentDiscount(10));
        when(viewManager.guideCreateVoucher()).thenReturn(voucherRequest);

        //when
        Power power = createCommand.execute();

        //then
        verify(viewManager, times(1)).guideCreateVoucher();
        verify(voucherController, times(1)).createVoucher(voucherRequest);

        assertThat(power).isEqualTo(Power.ON);
    }
}
