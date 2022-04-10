package com.waterfogsw.voucher;

import com.waterfogsw.voucher.console.Console;
import com.waterfogsw.voucher.console.Input;
import com.waterfogsw.voucher.console.Menu;
import com.waterfogsw.voucher.console.Output;
import com.waterfogsw.voucher.voucher.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.fail;

class ConsoleTest {

    Input input = new Console();
    Output output = new Console();

    //콘솔 입출력
    @Test
    @DisplayName("메뉴 입력")
    public void inputMenu() {
        // given
        String cmdLineInput = "list";
        consoleInput(cmdLineInput);

        //when
        var menu = input.inputMenu();

        //then
        Assertions.assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("바우처 타입 입력")
    public void inputVoucherType() {
        // given
        String cmdLineInput = "1";
        consoleInput(cmdLineInput);

        //when
        var type = input.inputVoucherType();

        //then
        Assertions.assertThat(type).isEqualTo(VoucherType.FIXED_AMOUNT);
    }

    @Test
    @DisplayName("percent 입력")
    public void inputPercent() {
        //given
        String cmdLineInput = "10";
        consoleInput(cmdLineInput);

        //when
        var percent = input.inputPercent();

        //then
        Assertions.assertThat(percent).isEqualTo(10d);
    }

    @Test
    @DisplayName("amount 입력")
    public void inputAmount() {
        //given
        String cmdLineInput = "10";
        consoleInput(cmdLineInput);

        //when
        var amount = input.inputAmount();

        //then
        Assertions.assertThat(amount).isEqualTo(10d);
    }

    @Test
    @DisplayName("존재하지 않는 메뉴인경우")
    public void menuCheck() {
        //given
        String cmdLineInput = "hello";
        consoleInput(cmdLineInput);

        //when, then
        try {
            var menu = input.inputMenu();
            fail();
        } catch (InvalidMenu e){
            //pass
        }
    }

    @Test
    @DisplayName("존재하지 않는 바우처 타입인경우")
    public void voucherTypeCheck() {
        //given
        String cmdLineInput = "123";
        consoleInput(cmdLineInput);

        //when, then
        try {
            var type = input.inputVoucherType();
            fail();
        } catch (InvalidVoucherType e){
            //pass
        }
    }

    private void consoleInput(String input) {
        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
}
