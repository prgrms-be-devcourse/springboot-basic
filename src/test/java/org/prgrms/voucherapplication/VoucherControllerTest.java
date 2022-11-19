package org.prgrms.voucherapplication;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.voucherapplication.console.Console;
import org.prgrms.voucherapplication.customer.CustomerService;
import org.prgrms.voucherapplication.customer.CustomerServiceImpl;
import org.prgrms.voucherapplication.voucher.controller.VoucherController;
import org.prgrms.voucherapplication.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.voucher.service.VoucherService;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("VoucherController 단위 테스트")
public class VoucherControllerTest {

    private static final VoucherService voucherService = mock(VoucherService.class);
    private static VoucherController voucherController;
    private static final CustomerService customerService = mock(CustomerServiceImpl.class);

    @BeforeAll
    static void constructor() {
        Console console = new Console();
        voucherController = new VoucherController(console, console, voucherService, customerService);
    }

    @Nested
    @DisplayName("유저가 어플리케이션을 실행할 때")
    class GivenStartApplication {

        @Test
        @DisplayName("지원가능한 명령어를 알려준다")
        void start() {
            final String description = "=== Voucher Program ===\n" +
                    "Type exit to exit the program.\n" +
                    "Type create to create a new voucher.\n" +
                    "Type list to list all vouchers.\n" +
                    "Type blacklist to list all customer_blacklist.";

            OutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            InputStream in = new ByteArrayInputStream("exit".getBytes());
            System.setIn(in);
            voucherController.start();

            assertThat(out).hasToString(description + "\n");
        }
    }

    @Nested
    @DisplayName("유저가 입력 가능한 명령어를 선택할 때")
    class CommandType {
        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DisplayName("대소문자 상관없이 create를 입력하면")
        class whenCreate {
            Stream<Arguments> createParam() {
                return Stream.of(
                        Arguments.of("create\n", "fixed\n", "100\n", "exit"),
                        Arguments.of("CREATE\n", "percent\n", "5\n", "EXit"),
                        Arguments.of("CReAtE\n", "percent\n", "10\n", "eXIT")
                );
            }

            private InputStream createInputStream(String commandType, String voucherName, String discount, String exit) {
                List<InputStream> streams = Arrays.asList(
                        new ByteArrayInputStream(commandType.getBytes()),
                        new ByteArrayInputStream(voucherName.getBytes()),
                        new ByteArrayInputStream(discount.getBytes()),
                        new ByteArrayInputStream(exit.getBytes()));
                return new SequenceInputStream(Collections.enumeration(streams));
            }

            @ParameterizedTest
            @MethodSource("createParam")
            @DisplayName("컨트롤러에 create 메소드가 실행된다.")
            void thenCreate(String commandType, String voucherName, String discount, String exit) {
                InputStream in = createInputStream(commandType, voucherName, discount, exit);
                System.setIn(in);

                voucherController.start();

                verify(voucherService, atLeastOnce()).create(any());
            }

            Stream<Arguments> voucherName() {
                return Stream.of(
                        Arguments.of("fixed", "FIXED"),
                        Arguments.of("percent", "PERCENT")
                );
            }

            @ParameterizedTest
            @MethodSource("voucherName")
            @DisplayName("원하는 바우처명, 할인을 입력하고 올바른 바우처가 맵핑된다.")
            void thenVoucherMapping(String inputName, String className) {
                InputStream in = new ByteArrayInputStream(inputName.getBytes());
                System.setIn(in);

                VoucherType voucherType = voucherController.getVoucherType();

                assertThat(voucherType).isEqualTo(VoucherType.valueOf(className));
            }
        }

        @Nested
        @DisplayName("list를 입력하면")
        class whenList {

            @Test
            @DisplayName("만들어진 바우처를 조회한다")
            void thenList() {
                InputStream in = new SequenceInputStream(new ByteArrayInputStream("lisT\n".getBytes()), new ByteArrayInputStream("exit".getBytes()));
                System.setIn(in);

                voucherController.start();

                verify(voucherService, times(1)).findAll();
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DisplayName("대소문자 상관없이 blacklist를 입력하면")
        class blacklist {

            Stream<Arguments> createParam() {
                return Stream.of(
                        Arguments.of("blacklist\n", "exit"),
                        Arguments.of("BLackList\n", "EXit"),
                        Arguments.of("BLACKLIST\n", "eXIT")
                );
            }

            @ParameterizedTest
            @MethodSource("createParam")
            @DisplayName("customerService의 findBlacklist()를 호출한다.")
            void thenPrint(String commandType, String exit) {
                InputStream in = new SequenceInputStream(new ByteArrayInputStream(commandType.getBytes()), new ByteArrayInputStream(exit.getBytes()));
                System.setIn(in);

                voucherController.start();

                verify(customerService, atLeastOnce()).findBlacklist();
            }
        }
    }


}
