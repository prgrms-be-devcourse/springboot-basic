package org.prgrms.voucher;

import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VoucherApplicationTest {

    @Nested
    @DisplayName("프로그램은")
    class DescribeProgram{

        @Nested
        @DisplayName("실행되면")
        class ContextRun{

            @Test
            @DisplayName("create, list, exit 메뉴가 화면에 출력된다.")
            void itPrintMenu(){

            }
        }
    }

    @Nested
    @DisplayName("사용자는")
    class DescribeSelect{

        @Nested
        @DisplayName("create 메뉴를 선택하면")
        class ContextCreate{

            @Test
            @DisplayName("바우처 정보를 입력받는다.")
            void itInputCreateVoucher(){

            }

            @Test
            @DisplayName("바우처를 생성한다.")
            void itCreateVoucher(){

            }
        }

        @Nested
        @DisplayName("list 메뉴를 선택하면")
        class ContextList{

            @Test
            @DisplayName("바우처 목록을 출력한다.")
            void itPrintVoucherList(){

            }
        }

        @Nested
        @DisplayName("exit 메뉴를 선택하면")
        class ContextExit{

            @Test
            @DisplayName("프로그램이 종료된다.")
            void itExitProgram(){

            }
        }

        @Nested
        @DisplayName("유효하지 않은 메뉴를 입력하면")
        class ContextInvalidInput{

            @Test
            @DisplayName("에러메시지가 출력된다.")
            void itPrintError(){

            }
        }
    }
}
