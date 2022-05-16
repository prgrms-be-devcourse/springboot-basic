package org.prgrms.springbootbasic.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.UUID;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.prgrms.springbootbasic.controller.Menu;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.exception.InvalidMenuInput;
import org.prgrms.springbootbasic.exception.InvalidVoucherTypeInput;
import org.prgrms.springbootbasic.exception.InvalidateUUIDFormat;

class ScannerViewTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public static InputStream generateUserInput(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    public static ScannerView generateScannerView(String input) {
        var in = generateUserInput(input);
        System.setIn(in);
        return new ScannerView(new Scanner(System.in), mock(CustomerBlackList.class));
    }

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(System.out);
        output.reset();
    }

    @DisplayName("메뉴 출력 테스트")
    @Test
    void printMenu() {
        //given
        ScannerView scannerView = new ScannerView(new Scanner(System.in),
            mock(CustomerBlackList.class));

        //when
        scannerView.printMenu();

        //then
        String format = "=== Voucher Program ===\n"
            + "Type EXIT to exit the program.\n"
            + "Type CREATE to create a new voucher.\n"
            + "Type LIST to list all vouchers.\n"
            + "Type BLACKLIST to list all customer black list.\n"
            + "Type CREATE_CUSTOMER to create a new customer.\n"
            + "Type LIST_CUSTOMER to list all customers.\n"
            + "Type ASSIGN_VOUCHER to assign voucher to customer.\n"
            + "Type LIST_CUSTOMER_VOUCHER to list customer's voucher.\n"
            + "Type DELETE_CUSTOMER_VOUCHER to delete customer's voucher.\n"
            + "Type LIST_CUSTOMER_HAVING_SPECIFIC_VOUCHER_TYPE to list customers having specific voucher type.\n\n";
        assertThat(output.toString()).hasToString(format);
    }

    @DisplayName("메뉴 입력 테스트 - 정상")
    @ParameterizedTest
    @EnumSource(Menu.class)
    void inputMenu(Menu menu) {
        //given
        var scannerView = generateScannerView(menu.name());

        //when
        var inputMenu = scannerView.inputMenu();

        //then
        assertThat(inputMenu).isEqualTo(menu);
    }

    @DisplayName("메뉴 입력 테스트 - 잘못된 입력")
    @Test
    void inputMenuInvalidInput() {
        //given
        var scannerView = generateScannerView("invalid");

        //when
        //then
        assertThatThrownBy(() -> scannerView.inputMenu())
            .isInstanceOf(InvalidMenuInput.class);
    }

    @DisplayName("바우처 타입 입력 테스트 - 정상")
    @ParameterizedTest
    @EnumSource(VoucherType.class)
    void selectVoucherType(VoucherType voucherType) {
        //given
        var scannerView = generateScannerView(voucherType.name());

        //when
        var selectVoucherType = scannerView.selectVoucherType();

        //then
        assertThat(selectVoucherType).isEqualTo(voucherType);
    }

    @DisplayName("바우처 타입 입력 테스트 - 잘못된 입력")
    @Test
    void selectVoucherTypeInvalidVoucherType() {
        //given
        var scannerView = generateScannerView("invalid");

        //when
        //then
        assertThatThrownBy(() -> scannerView.selectVoucherType())
            .isInstanceOf(InvalidVoucherTypeInput.class);
    }

    @DisplayName("amount 입력 테스트")
    @Test
    void selectAmount() {
        //given
        var scannerView = generateScannerView("1000");

        //when
        var amount = scannerView.selectAmount();

        //then
        assertThat(amount).isEqualTo(1000);
    }

    @DisplayName("percent 입력 테스트")
    @Test
    void selectPercent() {
        //given
        var scannerView = generateScannerView("20");

        //when
        var percent = scannerView.selectPercent();

        //then
        assertThat(percent).isEqualTo(20);
    }

    @DisplayName("사용자 이름 입력 테스트")
    @Test
    void selectName() {
        //given
        var scannerView = generateScannerView("test");

        //when
        var name = scannerView.selectName();

        //then
        assertThat(name).isEqualTo("test");
    }

    @DisplayName("사용자 이메일 입력 테스트")
    @Test
    void selectEmail() {
        //given
        var scannerView = generateScannerView("test@gmail.com");

        //when
        var email = scannerView.selectEmail();

        //then
        assertThat(email).isEqualTo("test@gmail.com");
    }

    @DisplayName("바우처 아이디 입력 - 정상")
    @Test
    void selectVoucherId() {
        //given
        var uuid = UUID.randomUUID();
        var scannerView = generateScannerView(uuid.toString());

        //when
        var voucherId = scannerView.selectVoucherId();

        //then
        assertThat(voucherId).isEqualTo(uuid);
    }

    @DisplayName("바우처 아이디 입력 - UUID 형식 틀림")
    @Test
    void selectVoucherIdInvalidFormat() {
        //given
        var scannerView = generateScannerView("invalidFormat");

        //when
        //then
        assertThatThrownBy(() -> scannerView.selectVoucherId())
            .isInstanceOf(InvalidateUUIDFormat.class);
    }

    @DisplayName("손님 아이디 입력 - 정상")
    @Test
    void selectCustomerId() {
        //given
        var uuid = UUID.randomUUID();
        var scannerView = generateScannerView(uuid.toString());

        //when
        var customerId = scannerView.selectCustomerId();

        //then
        assertThat(customerId).isEqualTo(uuid);
    }

    @DisplayName("손님 아이디 입력 - UUID 형식 오류")
    @Test
    void selectCustomerIdInvalidFormat() {
        //given
        var scannerView = generateScannerView("invalidFormat");

        //when
        //then
        assertThatThrownBy(() -> scannerView.selectCustomerId())
            .isInstanceOf(InvalidateUUIDFormat.class);
    }
}