package com.wonu606.vouchermanager.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.wonu606.vouchermanager.domain.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.io.ConsoleIO;
import com.wonu606.vouchermanager.util.UUIDGenerator;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VoucherConsoleInterface 테스트")
public class VoucherConsoleInterfaceTest {

    private ConsoleIO consoleIOMock;
    private VoucherController controllerMock;
    private UUIDGenerator uuidGeneratorMock;
    private VoucherConsoleInterface consoleInterface;

    @BeforeEach
    public void setUp() {
        consoleIOMock = mock(ConsoleIO.class);
        controllerMock = mock(VoucherController.class);
        uuidGeneratorMock = mock(UUIDGenerator.class);
        consoleInterface = new VoucherConsoleInterface(consoleIOMock, controllerMock,
                uuidGeneratorMock);
    }

    @DisplayName("exit가 입력으로 주어지고_run을 하면_프로그램이 종료된다.")
    @Test
    public void GivenExitMenuSelected_WhenRun_ThenTerminatesProgram() {
        // Given
        given(consoleIOMock.selectMenu()).willReturn(VoucherMenu.EXIT);

        // When
        consoleInterface.run();

        // Then
        then(consoleIOMock).should().terminal();
    }

    @DisplayName("list가 입력으로 주어지고_run을 하면_Voucher들을 출력한다.")
    @Test
    public void GivenListMenuSelected_WhenRun_ThenDisplayVoucherList() {
        // Given
        List<Voucher> voucherList = Arrays.asList(mock(Voucher.class), mock(Voucher.class));
        given(consoleIOMock.selectMenu()).willReturn(VoucherMenu.LIST, VoucherMenu.EXIT);
        given(controllerMock.getVoucherList()).willReturn(voucherList);

        // When
        consoleInterface.run();

        // Then
        then(consoleIOMock).should().displayVoucherList(voucherList);
    }

    @DisplayName("create가 입력으로 주어지고_run을 하면_Voucher를 생성하여 저장한다.")
    @Test
    public void GivenCreateMenuSelected_WhenRun_ThenCreateVoucher() {
        // Given
        UUID expectedUuid = UUID.randomUUID();
        VoucherDto voucherDto = new VoucherDto("FIXED", expectedUuid, 10.0);
        Voucher expectedVoucher = new FixedAmountVoucher(expectedUuid, new FixedAmountValue(10.0));

        given(consoleIOMock.selectMenu()).willReturn(VoucherMenu.CREATE, VoucherMenu.EXIT);
        given(consoleIOMock.selectVoucherType()).willReturn("FIXED");
        given(consoleIOMock.readDouble("discount")).willReturn(10.0);

        given(uuidGeneratorMock.generateUUID()).willReturn(expectedUuid);

        given(controllerMock.createVoucher(voucherDto)).willReturn(expectedVoucher);

        // When
        consoleInterface.run();

        // Then
        then(controllerMock).should().createVoucher(voucherDto);
    }
}
