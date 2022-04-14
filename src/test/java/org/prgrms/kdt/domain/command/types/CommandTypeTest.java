package org.prgrms.kdt.domain.command.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"exit", "create", "list", "blackList"})
    @DisplayName("유효한 명령어를 입력시 이에 해당하는 커맨드타입을 반환한다.")
    public void findCommandType(String command){
        //given
        //when
        CommandType commandType = CommandType.findCommand(command);
        //then
        assertThat(CommandType.values()).contains(commandType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"yes", "", " ", "exitList"})
    @DisplayName("지원하지 않는 명령어를 입력시 예외를 발생시킨다.")
    public void exception_findCommandType(String command){
        //given
        //when
        //then
        assertThatThrownBy(() -> CommandType.findCommand(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 명령어입니다.");
    }
}