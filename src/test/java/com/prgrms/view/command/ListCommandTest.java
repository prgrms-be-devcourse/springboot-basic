package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.model.voucher.dto.VoucherResponse;
import com.prgrms.view.ViewManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ListCommandTest {

    @Mock
    private VoucherController voucherController;
    @Mock
    private ViewManager viewManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void execute_ON_PowerON() {
        //given
        MockitoAnnotations.openMocks(this);
        ListCommand listCommand = new ListCommand(voucherController, viewManager);

        List<VoucherResponse> vouchers = new ArrayList<>();
        when(voucherController.listVoucher()).thenReturn(vouchers);

        //when
        Power power = listCommand.execute();

        //then
        verify(voucherController, times(1)).listVoucher();
        verify(viewManager, times(1)).viewVoucherList(vouchers);

        assertThat(power).isEqualTo(Power.ON);
    }
}
