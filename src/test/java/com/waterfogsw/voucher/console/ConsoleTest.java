package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.exception.InvalidAmountException;
import com.waterfogsw.voucher.exception.InvalidMenuException;
import com.waterfogsw.voucher.exception.InvalidPercentException;
import com.waterfogsw.voucher.exception.InvalidVoucherTypeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {

    Input input = new Console();

    //콘솔 입출력
    @ParameterizedTest
    @ValueSource(strings = {"list", "exit", "black_list", "create"})
    @DisplayName("메뉴 입력 - 존재하는 메뉴이면 성공")
    public void inputExistMenu(String consoleStringInput) {
        // given
        consoleInput(consoleStringInput);

        //when, then
        assertDoesNotThrow(() -> input.inputMenu());
    }

    @Test
    @DisplayName("메뉴 입력 - 존재하지 않는 메뉴이면 실패")
    public void inputInvalidMenu() {
        // given
        String consoleStringInput = "HollyMolly";
        consoleInput(consoleStringInput);

        //when, then
        assertThrows(InvalidMenuException.class, () -> input.inputMenu());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    @DisplayName("바우처 타입 입력 - 존재하는 바우처 타입 번호이면 성공")
    public void inputExistVoucherTypeNum(String consoleStringInput) {
        // given
        consoleInput(consoleStringInput);

        //when, then
        assertDoesNotThrow(() -> input.inputVoucherType());
    }

    @Test
    @DisplayName("바우처 타입 입력 - 존재하는 바우처 타입 번호이면 실패")
    public void inputInvalidVoucherTypeNum() {
        // given
        String consoleStringInput = "129387410928374";
        consoleInput(consoleStringInput);

        //when, then
        assertThrows(InvalidVoucherTypeException.class, () -> input.inputVoucherType());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "50", "100"})
    @DisplayName("percent 입력 - 유효한 범위 내면 성공")
    public void inputInRangePercent(String consoleStringInput) {
        //given
        consoleInput(consoleStringInput);

        //when, then
        assertDoesNotThrow(() -> input.inputPercent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "102"})
    @DisplayName("percent 입력 - 범위 밖이면 실패")
    public void inputOutOfRangePercent(String consoleStringInput) {
        //given
        consoleInput(consoleStringInput);

        //when, then
        assertThrows(InvalidPercentException.class, () -> input.inputPercent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "50", "10000"})
    @DisplayName("amount 입력 - 유효한 범위 내면 성공")
    public void inputInRangeAmount(String consoleStringInput) {
        //given
        consoleInput(consoleStringInput);

        //when, then
        assertDoesNotThrow(() -> input.inputPercent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-1000"})
    @DisplayName("amount 입력 - 범위 밖이면 실패")
    public void inputOutOfRangeAmount(String consoleStringInput) {
        //given
        consoleInput(consoleStringInput);

        //when, then
        assertThrows(InvalidAmountException.class, () -> input.inputPercent());
    }

    private static void consoleInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
}
