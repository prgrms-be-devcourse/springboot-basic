//package com.programmers;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.programmers.domain.FixedAmountVoucher;
//import com.programmers.domain.Voucher;
//import com.programmers.domain.VoucherType;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.util.NoSuchElementException;
//
//@SpringBootTest
//@ActiveProfiles("local")
//class VoucherControllerTest {
//
//    private static final String MENU_MESSAGE = "=== Voucher Program ===\n" +
//            "Type 'exit' or '1' to exit the program.\n" +
//            "Type 'create' or '2' to create a new voucher.\n" +
//            "Type 'list' or '3' to list all vouchers.\n" +
//            "Type 'black' or '4' to check the blacklist.";
//    private static final String VOUCHER_TYPE_MESSAGE = "\n=== Voucher Type ===\n" +
//            "Select voucher. (Type voucher name or number.)\n" +
//            "1. Fixed Amount Voucher\n" +
//            "2. Percent Discount Voucher";
//    private static final String DISCOUNT_VALUE_MESSAGE = "\n=== Type discount amount or rate ===";
//    private static final String VOUCHER_NAME_MESSAGE = "\n=== Type voucher name ===";
//    private static final String VOUCHER_CREATED_MESSAGE = "--- Voucher Created !! ---\n";
//    private static final String VOUCHER_LIST_TITLE_MESSAGE = "\n=== Voucher List ===";
//    private static final String BLACKLIST_MESSAGE = "\n=== Blacklist ===";
//
//    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    private final InputStream inputStream = System.in;
//    private final PrintStream printStream = System.out;
//
//    @Autowired
//    VoucherController voucherController;
//
//    @BeforeEach
//    public void before() {
//        System.setOut(new PrintStream(outputStream));
//    }
//
//    @AfterEach
//    public void after() {
//        System.setOut(printStream);
//        System.setIn(inputStream);
//    }
//
//    @DisplayName("바우처 컨트롤러 테스트")
//    @Test
//    void run() {
//        //given
//        String[] inputs = {"create", "1", "20", "f1", "create", "2", "50", "d1", "list", "exit"};
//        System.setIn(new ByteArrayInputStream(String.join("\n", inputs).getBytes()));
//
//        try {
//            //when
//            voucherController.run();
//            String output = outputStream.toString();
//
//            //then
//            assertTrue(output.contains(MENU_MESSAGE));
//            assertTrue(output.contains(VOUCHER_TYPE_MESSAGE));
//            assertTrue(output.contains(DISCOUNT_VALUE_MESSAGE));
//            assertTrue(output.contains(VOUCHER_NAME_MESSAGE));
//            assertTrue(output.contains(VOUCHER_CREATED_MESSAGE));
//            assertTrue(output.contains(VOUCHER_LIST_TITLE_MESSAGE));
//            assertTrue(output.contains("[ Voucher Type = Fixed Amount Voucher, Id = , discount amount = 20, voucher name = f1 ]"));
//            assertTrue(output.contains("[ Voucher Type = Percent Discount Voucher, Id = , discount percent = 50, voucher name = d1 ]"));
//        } catch (NoSuchElementException ignore) {
//        }
//    }
//
//    @DisplayName("바우처 이름 또는 숫자를 입력받는다")
//    @Test
//    void getVoucherType() {
//        //given
//        String input = "fixedamountvoucher";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        //when
//        VoucherType result = voucherController.getVoucherType();
//
//        //then
//        Assertions.assertThat(result).isEqualTo(VoucherType.FixedAmountVoucher);
//    }
//
//    @DisplayName("바우처를 생성한다")
//    @Test
//    void createVoucher() {
//        //given
//        String input = "fixedamountvoucher";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        try {
//            //when
//            Voucher result = voucherController.createVoucher();
//
//            //then
//            Assertions.assertThat(result).isInstanceOf(FixedAmountVoucher.class);
//        } catch (NoSuchElementException ignore) {
//        }
//    }
//
//    @DisplayName("바우처 정보를 입력받고 생성한다")
//    @Test
//    void makeVoucher() {
//        //given
//        String[] inputs = {"1", "20", "f1"};
//        System.setIn(new ByteArrayInputStream(String.join("\n", inputs).getBytes()));
//
//        try {
//            //when
//            Voucher result = voucherController.makeVoucher();
//            String output = outputStream.toString();
//
//            //then
//            assertTrue(output.contains(VOUCHER_TYPE_MESSAGE));
//            assertTrue(output.contains(DISCOUNT_VALUE_MESSAGE));
//            assertTrue(output.contains(VOUCHER_NAME_MESSAGE));
//
//            Assertions.assertThat(result).isInstanceOf(FixedAmountVoucher.class);
//        } catch (NoSuchElementException ignore) {
//        }
//    }
//
//    @DisplayName("주어진 입력이 숫자인지 검증한다")
//    @CsvSource(value = {
//            "145 : true",
//            "aa : false",
//            "0 : true",
//            "^%$ : false"
//    }, delimiter = ':')
//    @ParameterizedTest
//    void isNumeric(String input, boolean expected) {
//        //given
//        //when
//        boolean result = voucherController.isNumeric(input);
//
//        //then
//        assertThat(result).isEqualTo(expected);
//    }
//
//    @DisplayName("주어진 입력을 Long 타입으로 바꾸어 리턴한다")
//    @Test
//    void changeNumber() {
//        //given
//        String input = "123";
//
//        //when
//        Long result = voucherController.changeDiscountValueToNumber(input);
//
//        //then
//        Assertions.assertThat(result).isEqualTo(123L);
//    }
//
//    @DisplayName("주어진 입력이 숫자가 아니면 예외처리한다")
//    @Test
//    void changeNumberWithWrongInput() {
//        //given
//        //when
//        //then
//        Assertions.assertThatThrownBy(() -> voucherController.changeDiscountValueToNumber("abc"))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//}