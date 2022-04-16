package com.dojinyou.devcourse.voucherapplication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Voucher Application 시나리오 테스트")
class VoucherApplicationTests {

    @Nested
    @DisplayName("사용자가 어플리케이션을 실행할 때")
    class GivenExecuteApplication {
        @Nested
        @DisplayName("부팅이 완료 되었다면")
        class WhenCompleteExecute {
            @Test
            @DisplayName("사용가능한 명령어 리스트를 출력한다")
            void thenDisplayCommandList() {
                // TODO: 테스트코드 작성
            }

            @Test
            @DisplayName("부팅 행위와 부팅 시간을 정보로 로깅한다")
            void thenLoggingInformationOfBehaviorAndTime() {
                // TODO: 테스트코드 작성
            }
        }

    }

    @Nested
    @DisplayName("사용자가 입력 가능한 명령어가 create, list, exit일 때")
    class GivenUserCommandMode {

        @Nested
        @DisplayName("exit 명령어를 입력하면")
        class WhenInputCommandExit {

            @Test
            @DisplayName("어플리케이션을 종료한다")
            void thenTerminateApplication() {
                // TODO: 테스트코드 작성
            }

            @Test
            @DisplayName("요청 명령과 명령어와 명령 시간을 정보로 로깅한다")
            void thenLoggingInformationOfBehaviorAndInputValueAndTime() {
                // TODO: 테스트코드 작성
            }
        }

        @Nested
        @DisplayName("create 명령어를 입력하면")
        class WhenInputCommandCreate {

            @Test
            @DisplayName("바우처 생성을 위한 정보를 입력 받아야한다.")
            void thenInputModeForVoucherCreate() {
                // TODO: 테스트코드 작성
            }

            @Test
            @DisplayName("요청 명령과 명령어와 명령 시간을 정보로 로깅한다")
            void thenLoggingInformationOfBehaviorAndInputValueAndTime() {
                // TODO: 테스트코드 작성
            }
        }

        @Nested
        @DisplayName("list 명령어를 입력하면")
        class WhenInputCommandList {
            @Test
            @DisplayName("생성된 바우처 리스트를 출력한다")
            void thenDisplayVoucherList() {
                // TODO: 테스트코드 작성
            }

            @Test
            @DisplayName("요청 명령과 명령어와 명령 시간을 정보로 로깅한다")
            void thenLoggingInformationOfBehaviorAndInputValueAndTime() {
                // TODO: 테스트코드 작성
            }
        }

        @Nested
        @DisplayName("명령어가 아닌 다른 입력을 한다면")
        class WhenInputIllegalCommand {
            @Test
            @DisplayName("에러 메세지를 출력한다")
            void thenDisplayErrorMessage() {
                // TODO: 테스트코드 작성
            }

            @Test
            @DisplayName("요청 명령과 명령어와 실패 시간을 정보로써 로깅한다")
            void thenLoggingInformationOfBehaviorAndInputValueAndTime() {
                // TODO: 테스트코드 작성
            }
        }
    }

}
