package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.ViewManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ExitCommandTest {
    @Mock
    private ViewManager viewManager;
    @Mock
    private VoucherController voucherController;

    @Test
    @DisplayName("ExitCommand의 execute가 Power의 OFF를 올바르게 호출한다.")
    void execute_On_PowerOFF() {
        //given
        MockitoAnnotations.openMocks(this);
        ExitCommand exitCommand = new ExitCommand(voucherController, viewManager);

        //when
        Power power = exitCommand.execute();

        //then
        verify(viewManager, times(1)).guideClose();

        assertThat(power).isEqualTo(Power.OFF);
    }
}
