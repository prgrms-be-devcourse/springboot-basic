package org.prgrms.kdt.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.exceptions.InvalidITypeInputException;

import static org.junit.jupiter.api.Assertions.*;

class SelectTypeTest {

    @Test
    @DisplayName("create를 콘솔에서 입력받으면 바우처 생성 타입을 반환한다.")
    void testSelectCreate() {
        // given
        String input = "create";
        // when
        SelectType createType = SelectType.findSelectType(input);
        // then
        assertEquals(SelectType.CREATE, createType);
    }

    @Test
    @DisplayName("list를 콘손에서 입력받으면 바우처 목록 조회 타입을 반환한다.")
    void testSelectList() {
        // given
        String input = "list";
        // when
        SelectType listType = SelectType.findSelectType(input);
        // then
        assertEquals(SelectType.LIST, listType);
    }

    @Test
    @DisplayName("exit를 콘솔에서 입력받으면 프로그램 종료 타입을 반환한다.")
    void testSelectExit() {
        // given
        String input = "exit";
        // when
        SelectType exitType = SelectType.findSelectType(input);
        // then
        assertEquals(SelectType.EXIT, exitType);
    }


    @DisplayName("해당하지 않는 값을 입력할 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"what", "1", "0", "out", "crete", "liat"})
    void testInvalidSelectInput(String input) {
        assertThrows(InvalidITypeInputException.class, () -> {
            SelectType.findSelectType(input);
        });
    }
}