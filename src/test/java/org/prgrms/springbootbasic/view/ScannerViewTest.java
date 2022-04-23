package org.prgrms.springbootbasic.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.prgrms.springbootbasic.controller.Menu;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.exception.InvalidMenuInput;
import org.prgrms.springbootbasic.exception.InvalidVoucherTypeInput;
import org.prgrms.springbootbasic.exception.InvalidateUUIDFormat;

class ScannerViewTest {

    public static InputStream generateUserInput(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    public static ScannerView generateScannerView(String input) {
        var in = generateUserInput(input);
        System.setIn(in);
        return new ScannerView(new Scanner(System.in), mock(CustomerBlackList.class));
    }

    @DisplayName("메뉴 출력 테스트")
    @Test
    void printMenu() {
        //given
        ScannerView scannerView = new ScannerView(new Scanner(System.in),
            mock(CustomerBlackList.class));

        //when
        //then
        scannerView.printMenu();
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

    @DisplayName("voucher list 출력 테스트")
    @Test
    void printList() {
        //given
        var vouchers = List.of(
            new FixedAmountVoucher(UUID.randomUUID(), 1000),
            new PercentDiscountVoucher(UUID.randomUUID(), 20));

        //when
        new ScannerView(new Scanner(System.in), mock(CustomerBlackList.class)).printList(vouchers);

        //then
    }

    @DisplayName("블랙리스트 출력 테스트")
    @Test
    void printCustomerBlackList() {
        //given
        var customerBlackList = mock(CustomerBlackList.class);
        var scannerView = new ScannerView(new Scanner(System.in), customerBlackList);

        //when
        scannerView.printCustomerBlackList();

        //then
        verify(customerBlackList).printCustomerBlackList();
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

    @DisplayName("사용자 목록 출력 테스트")
    @Test
    void printAllCustomers() {
        //given
        var customers = List.of(
            new Customer(UUID.randomUUID(), "test", "test@gmail.com"),
            new Customer(UUID.randomUUID(), "test2", "test2@gmail.com")
        );

        //when
        generateScannerView("").printAllCustomers(customers);
        //then
    }

    @DisplayName("비지니스 에러 출력")
    @Test
    void printError() {
        //given
        //when
        generateScannerView("").printError("Got Service Error!");

        //then
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

    @DisplayName("특정 손님 바우처 목록 출력")
    @Test
    void printCustomerVouchers() {
        //given
        var vouchers = List.of(
            new FixedAmountVoucher(UUID.randomUUID(), 1000),
            new PercentDiscountVoucher(UUID.randomUUID(), 20)
        );

        //when
        generateScannerView("").printCustomerVouchers(vouchers);
    }
}