package com.dojinyou.devcourse.voucherapplication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    @Nested
    @DisplayName("of 함수에 대한 테스트")
    class Describe_of_Method {

        @Nested
        @DisplayName("잘못된 값이 들어온다면,")
        class Context_Illegal_User_Input {

            @ParameterizedTest
            @ValueSource(strings = {"", "null", "아무값이나 넣어"})
            @DisplayName("예외를 발생시킨다.")
            void it_Throws_IllegalArgumentException(String userInput) {
                assertThatIllegalArgumentException().isThrownBy(()->Command.of(userInput));
            }
            @Test
            @DisplayName("예외를 발생시킨다.(null 입력)")
            void it_Throws_IllegalArgumentException_With_NULL() {
                assertThatIllegalArgumentException().isThrownBy(()->Command.of(null));
            }
        }

        @Nested
        @DisplayName("정상적인 값이 들어온다면,")
        class Context_Correct_User_Input {

            @Test
            @DisplayName("Command를 생성하여 돌려준다.")
            void it_Return_Command() {
                assertAll(
                        () -> assertTrue(Command.of("CREATE").equals(Command.CREATE),
                                         "Create Command 생성 테스트 실패"),
                        () -> assertTrue(Command.of("LIST").equals(Command.LIST),
                                         "LIST Command 생성 테스트 실패"),
                        () -> assertTrue(Command.of("EXIT").equals(Command.EXIT),
                                         "EXIT Command 생성 테스트 실패")
                );
            }
        }
    }
}