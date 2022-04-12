package org.prgrms.kdt.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.service.VoucherService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Test
    @DisplayName("App으로부터 findAll 요청이 들어오면 요청을 서비스로 넘겨준다. 바우처 서비스에서 voucherList가 호출됐는지 확인한다.")
    void findAll() {

        //given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        Controller controller = new Controller(voucherServiceMock);

        //when
        controller.findAll();

        //then
        verify(voucherServiceMock).voucherList();
    }

    @Test
    @DisplayName("App에서 create요청을 받으면 voucherService로 넘겨주는데 voucherService에서 create가 호출되는지 확인한다.")
    void create() {

        //given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        Controller controller = new Controller(voucherServiceMock);

        //when
        controller.create(10, "fixed");

        //then
        verify(voucherServiceMock).create(10, "fixed");
    }
}