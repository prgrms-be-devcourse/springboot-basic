package com.dojinyou.devcourse.voucherapplication;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
@DisplayName("Voucher Application 시나리오 테스트")
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherApplicationTests {
    private static MockedStatic<VoucherApplication> voucherApplication;
    @BeforeAll
    static void registStaticApplication() {
        voucherApplication = mockStatic(VoucherApplication.class);
    }

    @AfterAll
    static void closeApplication() {
        voucherApplication.close();
    }

    @Nested
    @DisplayName("사용자가 어플리케이션을 실행할 때")
    class GivenExecuteApplication {
        @Nested
        @DisplayName("부팅이 완료 되었다면")
        class WhenCompleteExecute {
            private final ByteArrayOutputStream output = new ByteArrayOutputStream();


            @BeforeEach
            void startApp() {
                System.setOut(new PrintStream(output));
            }

            @Test
            @DisplayName("사용가능한 명령어 리스트를 출력한다")
            void thenDisplayCommandList() {
                // Application 실행
                VoucherApplication.main(new String[]{});
                StringBuilder sb = new StringBuilder();
                sb.append("=== Voucher Program ===\n");
                sb.append("Type exit to exit the program.\n");
                sb.append("Type create to create a new voucher.\n");
                sb.append("Type list to list all vouchers.\n");
                String expectedCommandListMessage = sb.toString();

                assertThat(output.toString()).isEqualTo(expectedCommandListMessage);
            }

            @Test
            @DisplayName("부팅 행위와 부팅 시간을 정보로 로깅한다")
            void thenLoggingInformationOfBehaviorAndTime() {
                // TODO: 테스트코드 작성
            }

            @AfterEach
            void endApp() {
                System.setOut(System.out);
                output.reset();
            }

        }

    }

    @Nested
    @TestClassOrder(ClassOrderer.OrderAnnotation.class)
    @DisplayName("사용자가 입력 가능한 명령어가 create, list, exit일 때")
    class GivenUserCommandMode {
        public InputStream generateUserInput(String input) {
            return new ByteArrayInputStream(input.getBytes());
        }

        @BeforeAll
        void setUp(){}

        @Nested
        @DisplayName("exit 명령어를 입력하면")
        @Order(3)
        class WhenInputCommandExit {

            @ParameterizedTest
            @ValueSource(strings = {"exit","EXIT","exiT","Exit"})
            @DisplayName("어플리케이션을 종료한다")
            void thenTerminateApplication(String userInput) {
                VoucherApplication.main(new String[]{});
                InputStream inputStream = generateUserInput(userInput);
                System.setIn(inputStream);
                Scanner sc = new Scanner(System.in);
                InputView.setScanner(sc);
                // 어플리케이션 종료된 것을 어떻게 확인할 수 있는지??
            }
        }

        @Nested
        @Order(1)
        @DisplayName("create 명령어를 입력하면")
        class WhenInputCommandCreate {
            private final ByteArrayOutputStream output = new ByteArrayOutputStream();

            @ParameterizedTest
            @ValueSource(strings = {"create","CREATE","Create"})
            @DisplayName("바우처 생성을 위한 정보를 입력 받아야한다.")
            void thenInputModeForVoucherCreate(String inputCommand) {
                VoucherApplication.main(new String[]{});
                InputStream inputCommandStream = generateUserInput(inputCommand);
                System.setIn(inputCommandStream);
                Scanner sc = new Scanner(System.in);
                StringBuilder sb = new StringBuilder();
                sb.append("=== Voucher Type ===\n");
                sb.append("1. FixedAmountType\n");
                sb.append("2. PercentAmountType\n");
                sb.append("Type : ");
                String expectedCommandListMessage = sb.toString();

                InputView.setScanner(sc);
                System.setOut(new PrintStream(output));
                assertThat(output.toString()).isEqualTo(expectedCommandListMessage);
            }        }

        @Nested
        @Order(2)
        @DisplayName("list 명령어를 입력하면")
        class WhenInputCommandList {
            private final ByteArrayOutputStream output = new ByteArrayOutputStream();

            @Autowired
            private final VoucherRepository voucherRepository;

            @ParameterizedTest
            @ValueSource(strings = {"list","LIST","List"})
            @DisplayName("생성된 바우처 리스트를 출력한다")
            void thenDisplayVoucherList(String inputCommand) {
                InputStream inputCommandStream = generateUserInput(inputCommand);
                System.setIn(inputCommandStream);
                Scanner sc = new Scanner(System.in);
                StringBuilder sb = new StringBuilder();
                VoucherType sampleVoucherType = VoucherType.FIXED_AMOUNT;
                int sampleVoucherAmount = 5000;
                LocalDateTime sampleCreateAt = LocalDateTime.now();

                Voucher sampleVoucher = new Voucher(sampleVoucherType,
                                                    sampleVoucherAmount,
                                                    sampleCreateAt);

                voucherRepository.save(sampleVoucher);

                sb.append("=== Voucher List ===");
                sb.append(sampleVoucher.toString());
                sb.append("====================");
                String expectedCommandListMessage = sb.toString();

                InputView.setScanner(sc);
                System.setOut(new PrintStream(output));
                assertThat(output.toString()).isEqualTo(expectedCommandListMessage);
            }
        }

        @Nested
        @Order(1)
        @DisplayName("명령어가 아닌 다른 입력을 한다면")
        class WhenInputIllegalCommand {
            @ParameterizedTest
            @ValueSource(strings = {"dojin","listz","cret"})
            @DisplayName("에러 메세지를 출력한다")
            void thenDisplayErrorMessage(String userWrongInput) {
                InputStream inputCommandStream = generateUserInput(userWrongInput);
                System.setIn(inputCommandStream);
                InputView.setScanner(sc);

                Scanner sc = new Scanner(System.in);
                StringBuilder sb = new StringBuilder();
                sb.append(userWrongInput);
                sb.append(": command not found\n");
                String expectedCommandListMessage = sb.toString();

                System.setOut(new PrintStream(output));
                assertThat(output.toString()).isEqualTo(expectedCommandListMessage);
            }
        }
    }
}
