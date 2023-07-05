package com.programmers.io;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.domain.voucher.FixedAmountVoucher;
import com.programmers.domain.voucher.Voucher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class ConsoleTest {

    private static final String MENU_MESSAGE =
            "=== Voucher Program ===\n" +
                    "Type 'exit' or '1' to exit the program.\n" +
                    "Type 'create' or '2' to create a new voucher or a new customer.\n" +
                    "Type 'list' or '3' to list all vouchers or all customers.\n" +
                    "Type 'update' or '4' to update a voucher or a customer.\n" +
                    "Type 'delete' or '5' to delete a voucher or a customer.";
    private static final String VOUCHER_TYPE_MESSAGE =
            "\n=== Voucher Type ===\n" +
                    "Type voucher name or number to create.\n" +
                    "1. Fixed Amount Voucher\n" +
                    "2. Percent Discount Voucher";
    private static final String DISCOUNT_VALUE_MESSAGE = "\n=== Type discount amount or rate ===";
    private static final String VOUCHER_NAME_MESSAGE = "\n=== Type voucher name ===";
    private static final String VOUCHER_CREATED_MESSAGE = "--- Voucher Created !! ---\n";
    private static final String VOUCHER_LIST_TITLE_MESSAGE = "\n=== Voucher List ===";
    private static final String BLACKLIST_MESSAGE = "\n=== Blacklist ===";
    private static final String CREATE_MESSAGE =
            "\n=== Create ===\n" +
                    "Type '1' or '2' to create item.\n" +
                    "1. voucher\n" +
                    "2. customer";
    private static final String LIST_MESSAGE =
            "\n=== List ===\n" +
                    "Type '1' or '2' to list items.\n" +
                    "1. voucher\n" +
                    "2. customer";
    private static final String CUSTOMER_LIST_MESSAGE =
            "\n=== Customer List ===\n" +
                    "Type '1' or '2' to choose Customer Type.\n" +
                    "1. normal customer\n" +
                    "2. blacklist";
    private static final String UPDATE_MESSAGE =
            "\n=== Update ===\n" +
                    "Type '1' or '2' to update item.\n" +
                    "1. voucher\n" +
                    "2. normal customer";
    private static final String DELETE_MESSAGE =
            "\n=== Delete ===\n" +
                    "Type '1' or '2' to delete item.\n" +
                    "1. voucher\n" +
                    "2. normal customer";
    private static final String DELETE_TYPE_VOUCHER_SELECTION_MESSAGE =
            "=== Delete ===\n" +
                    "Type '1' or '2' to delete type.\n" +
                    "1. Delete one voucher.\n" +
                    "2. Delete all vouchers.";
    private static final String DELETE_TYPE_CUSTOMER_SELECTION_MESSAGE =
            "\n=== Delete ===\n" +
                    "Type '1' or '2' to delete type.\n" +
                    "1. Delete one customer.\n" +
                    "2. Delete all customers.";

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = System.out;

    private final Console console = new Console();

    @BeforeEach
    public void before() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void after() {
        System.setOut(printStream);
    }

    @DisplayName("메뉴를 출력한다")
    @Test
    void printMenu() {
        //given
        //when
        console.printMenu();

        //then
        assertThat(outputStream.toString()).contains(MENU_MESSAGE);
    }

    @DisplayName("값을 입력받는다")
    @ValueSource(strings = {"1", "2", "3"})
    @ParameterizedTest
    void readInput(String input) {
        //given
        //when
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String result = console.readInput();

        //then
        assertThat(result).contains(input);
    }

    @DisplayName("바우처 타입을 출력한다")
    @Test
    void printVoucherType() {
        //given
        //when
        console.printVoucherType();

        //then
        assertThat(outputStream.toString()).contains(VOUCHER_TYPE_MESSAGE);
    }

    @DisplayName("바우처 할인 값 제목을 출력한다")
    @Test
    void printDiscountValueInput() {
        //given
        //when
        console.printDiscountValueInput();

        //then
        assertThat(outputStream.toString()).contains(DISCOUNT_VALUE_MESSAGE);
    }

    @DisplayName("바우처 이름 제목을 출력한다")
    @Test
    void printVoucherNameInput() {
        //given
        //when
        console.printVoucherNameInput();

        //then
        assertThat(outputStream.toString()).contains(VOUCHER_NAME_MESSAGE);
    }

    @DisplayName("바우처 생성 완료 문구를 출력한다")
    @Test
    void printVoucherCreated() {
        //given
        //when
        console.printVoucherCreated();

        //then
        assertThat(outputStream.toString()).contains(VOUCHER_CREATED_MESSAGE);
    }

    @DisplayName("바우처 리스트 제목을 출력한다")
    @Test
    void printVoucherList() {
        //given
        //when
        console.printVoucherListTitle();

        //then
        assertThat(outputStream.toString()).contains(VOUCHER_LIST_TITLE_MESSAGE);
    }

    @DisplayName("바우처 목록을 출력한다")
    @Test
    void printVouchers() {
        //given
        FixedAmountVoucher f1 = new FixedAmountVoucher(UUID.randomUUID(), "voucherName1", 11L);
        FixedAmountVoucher f2 = new FixedAmountVoucher(UUID.randomUUID(), "voucherName2", 12L);
        List<Voucher> vouchers = Arrays.asList(f1, f2);

        //when
        console.printVouchers(vouchers);

        //then
        assertThat(outputStream.toString()).contains("voucherName1");
        assertThat(outputStream.toString()).contains("voucherName2");
    }

    @DisplayName("블랙리스트 제목을 출력한다")
    @Test
    void printBlacklistTitle() {
        //given
        //when
        console.printBlacklistTitle();

        //then
        assertThat(outputStream.toString()).contains(BLACKLIST_MESSAGE);
    }

    @DisplayName("Create 메시지를 출력한다")
    @Test
    void printCreateMessage() {
        //given
        //when
        console.printCreateMessage();

        //then
        assertThat(outputStream.toString()).contains(CREATE_MESSAGE);
    }

    @DisplayName("List 메시지를 출력한다")
    @Test
    void printListMessage() {
        //given
        //when
        console.printListMessage();

        //then
        assertThat(outputStream.toString()).contains(LIST_MESSAGE);
    }

    @DisplayName("Customer List 메시지를 출력한다")
    @Test
    void printCustomerListMessage() {
        //given
        //when
        console.printCustomerListMessage();

        //then
        assertThat(outputStream.toString()).contains(CUSTOMER_LIST_MESSAGE);
    }

    @DisplayName("Update 메시지를 출력한다")
    @Test
    void printUpdateMessage() {
        //given
        //when
        console.printUpdateMessage();

        //then
        assertThat(outputStream.toString()).contains(UPDATE_MESSAGE);
    }

    @DisplayName("Delete 메시지를 출력한다")
    @Test
    void printDeleteMessage() {
        //given
        //when
        console.printDeleteMessage();

        //then
        assertThat(outputStream.toString()).contains(DELETE_MESSAGE);
    }

    @DisplayName("바우처 삭제 종류를 고르는 메뉴 메시지를 출력한다")
    @Test
    void printDeleteTypeVoucherSelectionMessage() {
        //given
        //when
        console.printDeleteTypeVoucherSelectionMessage();

        //then
        assertThat(outputStream.toString()).contains(DELETE_TYPE_VOUCHER_SELECTION_MESSAGE);
    }

    @DisplayName("회원 삭제 종류를 고르는 메뉴 메시지를 출력한다")
    @Test
    void printDeleteTypeCustomerSelectionMessage() {
        //given
        //when
        console.printDeleteTypeCustomerSelectionMessage();

        //then
        assertThat(outputStream.toString()).contains(DELETE_TYPE_CUSTOMER_SELECTION_MESSAGE);
    }
}