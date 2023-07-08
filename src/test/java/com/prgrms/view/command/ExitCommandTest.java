package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.dto.VoucherRequest;
import com.prgrms.model.voucher.dto.discount.PercentDiscount;
import com.prgrms.view.ViewManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExitCommandTest {
    @Mock
    private ViewManager viewManager;

    @BeforeEach
    void setUp() {
    }
    @Test
    void Execute_ON_PowerOFF() {
        //given
        MockitoAnnotations.openMocks(this);
        ExitCommand exitCommand = new ExitCommand(viewManager);

        //when
        Power power = exitCommand.execute();

        //then
        verify(viewManager, times(1)).guideClose();

        assertThat(power).isEqualTo(Power.OFF);
    }
}
