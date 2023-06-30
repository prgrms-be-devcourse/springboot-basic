package org.prgrms.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTypeTest {

    @ParameterizedTest
    @CsvSource({"Create","list","exit"})
    @DisplayName("각 커맨드 타입의 입력에 따른 결과 변화를 확인한다.")
    void testValidCommandType(String commandType){
        CommandType bySelection = CommandType.findBySelection(commandType);
        CommandType expectedType = CommandType.valueOf(commandType.toUpperCase());

        assertThat(bySelection).isEqualTo(expectedType);
    }

    @ParameterizedTest
    @CsvSource({"sdfsdfdd","watgwg","ryjryj"})
    @DisplayName("실페 케이스 : commandType에서 지정하지 않은 값 입력")
    void failWrongType(String commandType){
        assertThatThrownBy(() -> CommandType.findBySelection(commandType))
                .isInstanceOf(IllegalArgumentException.class);
    }


}