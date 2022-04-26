package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.controller.VoucherConsoleController;
import kdt.vouchermanagement.domain.voucher.converter.VoucherRequestConverter;
import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.global.io.Input;
import kdt.vouchermanagement.global.io.Output;
import kdt.vouchermanagement.global.response.Response;
import kdt.vouchermanagement.global.view.ConsoleDispatcher;
import kdt.vouchermanagement.global.view.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ConsoleDispatcherTest {

    @InjectMocks
    ConsoleDispatcher consoleDispatcher;

    @Mock
    Input consoleInput;

    @Mock
    Output consoleOutPut;

    @Mock
    VoucherRequestConverter voucherRequestConverter;

    @Mock
    VoucherConsoleController voucherConsoleController;

    VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 1);
    Voucher voucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 1);
    Response<String> errorResponse = Response.of(400, "예외가 발생하였습니다.");
    Response<Voucher> createVoucherResponse = Response.of(200, voucher);
    Response<List> listVouchersResponse = Response.of(200, List.of(voucher));

    @Test
    @DisplayName("입력된 메뉴가 유효하지 않은 메뉴이면_실패")
    void getInvalidMenu() {
        //given
        String input = "hello";

        try {
            Method findMenu = consoleDispatcher.getClass().getDeclaredMethod("findMenu", String.class);
            findMenu.setAccessible(true);

            //then
            Object menu = findMenu.invoke(consoleDispatcher, input);

            //then
            assertThat(menu).isEqualTo(Menu.NONE);
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("입력된 메뉴가 유효한 메뉴이면_성공")
    void getValidMenu() {
        //given
        String input = "create";

        try {
            Method findMenu = consoleDispatcher.getClass().getDeclaredMethod("findMenu", String.class);
            findMenu.setAccessible(true);

            //when
            Object menu = findMenu.invoke(consoleDispatcher, input);

            //then
            assertThat(menu).isEqualTo(Menu.CREATE_VOUCHER);
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("메뉴 입력 안내를 위한 콘솔 출력 요청(consoleOutput printMenu 메소드 호출)_성공")
    void requestPrintMenu() {
        try {
            //given
            Method commandPrintMenu = consoleDispatcher.getClass().getDeclaredMethod("commandPrintMenu");
            commandPrintMenu.setAccessible(true);

            //when
            commandPrintMenu.invoke(consoleDispatcher);

            //then
            verify(consoleOutPut, times(1)).printMenu();
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("바우처 생성할 때 Voucher Type 번호와 Discount Value 입력 안내 콘솔 출력 요청(consoleOutput printType, printValue 메소드 호출)_성공")
    void requstPrintTypeAndValue() {
        //given
        doReturn(1).when(consoleInput).valueInput();
        doReturn(request).when(voucherRequestConverter).of(any(Integer.class), any(Integer.class));
        doReturn(errorResponse).when(voucherConsoleController).create(any(VoucherRequest.class));

        try {
            //given
            Method commandCreateVoucher = consoleDispatcher.getClass().getDeclaredMethod("commandCreateVoucher");
            commandCreateVoucher.setAccessible(true);

            //when
            commandCreateVoucher.invoke(consoleDispatcher);

            //then
            verify(consoleOutPut, times(1)).printType();
            verify(consoleOutPut, times(1)).printValue();
            verify(consoleOutPut, times(1)).printError(errorResponse);
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("생성된 바우처 콘솔 출력 요청(consoleOutput printCreateVoucher 메소드 호출)_성공")
    void requestPrintCreateVoucher() {
        //given
        doReturn(1).when(consoleInput).valueInput();
        doReturn(request).when(voucherRequestConverter).of(any(Integer.class), any(Integer.class));
        doReturn(createVoucherResponse).when(voucherConsoleController).create(any(VoucherRequest.class));

        try {
            Method commandCreateVoucher = consoleDispatcher.getClass().getDeclaredMethod("commandCreateVoucher");
            commandCreateVoucher.setAccessible(true);

            //when
            commandCreateVoucher.invoke(consoleDispatcher);

            //then
            verify(consoleOutPut, times(1)).printCreateVoucher(createVoucherResponse);
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("바우처 목록 콘솔 출력 요청(consoleOutput printListVouchers 메소드 호출)_성공")
    void requestPrintListVouchers() {
        //given
        doReturn(listVouchersResponse).when(voucherConsoleController).getVouchers();

        try {
            Method commandListVouchers = consoleDispatcher.getClass().getDeclaredMethod("commandListVouchers");
            commandListVouchers.setAccessible(true);

            //when
            commandListVouchers.invoke(consoleDispatcher);

            //then
            verify(consoleOutPut, times(1)).printListVouchers(listVouchersResponse);
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("Error Response 콘솔 출력 요청_성공")
    void requestPrintError() {
        //given
        doReturn(errorResponse).when(voucherConsoleController).getVouchers();

        try {
            Method commandListVouchers = consoleDispatcher.getClass().getDeclaredMethod("commandListVouchers");
            commandListVouchers.setAccessible(true);

            //when
            commandListVouchers.invoke(consoleDispatcher);

            //then
            verify(consoleOutPut, times(1)).printError(errorResponse);
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("잘못된 메뉴 입력 안내를 위한 콘솔 출력")
    void requestPrintNoneMenu() {
        //given
        try {
            Method commandPrintNoneMenu = consoleDispatcher.getClass().getDeclaredMethod("commandPrintNoneMenu");
            commandPrintNoneMenu.setAccessible(true);

            //when
            commandPrintNoneMenu.invoke(consoleDispatcher);

            //then
            verify(consoleOutPut, times(1)).printNoneMenu();
        } catch (NoSuchMethodException e) {
            new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            new RuntimeException(e.getMessage());
        }
    }
}