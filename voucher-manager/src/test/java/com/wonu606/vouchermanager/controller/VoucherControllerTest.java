package com.wonu606.vouchermanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wonu606.vouchermanager.io.ConsoleIO;
import com.wonu606.vouchermanager.repository.LocalMemoryVoucherRepository;
import com.wonu606.vouchermanager.service.VoucherService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class VoucherControllerTest {

    @Test
    void exit를_입력하면_프로그램_terminal을_호출해야_한다() {
        // given
        VoucherService serviceMock = mock(VoucherService.class);

        ConsoleIO consoleIOMock = mock(ConsoleIO.class);
        when(consoleIOMock.selectMenu()).thenReturn("exit");

        VoucherController voucherController = new VoucherController(serviceMock, consoleIOMock);

        // when
        voucherController.run();

        // then
        verify(consoleIOMock, times(1)).selectMenu();
        verify(consoleIOMock).terminal();
    }

    @Test
    void list를_입력하면_바우처리스트를_출력해야_한다() {
        // given
        VoucherService serviceMock = mock(VoucherService.class);

        ConsoleIO consoleIOMock = mock(ConsoleIO.class);
        when(consoleIOMock.selectMenu()).thenReturn("list").thenReturn("exit");

        VoucherController voucherController = new VoucherController(serviceMock, consoleIOMock);

        // when
        voucherController.run();

        // then
        verify(consoleIOMock, times(2)).selectMenu();
        verify(consoleIOMock, times(1)).displayVoucherList(new ArrayList<>());
        verify(consoleIOMock).terminal();
    }

    @Test
    void create를_입력하면_바우처를_생성하고_저장할_수_있다() {
        // given
        ConsoleIO consoleIOMock = mock(ConsoleIO.class);
        when(consoleIOMock.selectMenu()).thenReturn("create").thenReturn("exit");
        when(consoleIOMock.selectVoucherType()).thenReturn("fixed");
        when(consoleIOMock.readDouble("discount")).thenReturn(5000d);

        VoucherService service = new VoucherService(new LocalMemoryVoucherRepository());
        int prevSize = service.getVoucherList().size();

        VoucherController voucherController = new VoucherController(service, consoleIOMock);

        // when
        voucherController.run();

        // then
        verify(consoleIOMock, times(2)).selectMenu();
        verify(consoleIOMock, never()).displayMessage("존재하지 않는 바우처 타입입니다.");
        verify(consoleIOMock, times(1)).terminal();
        assertEquals(prevSize + 1, service.getVoucherList().size());
    }
}