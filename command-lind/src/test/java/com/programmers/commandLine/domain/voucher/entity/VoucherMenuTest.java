package com.programmers.commandLine.domain.voucher.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *  VoucherMenuTest의 설명을 여기에 작성한다.
 *
 *  @author 장주영
 *  @version 1.0.0
 *  작성일 2022/11/07
 *
**/

class VoucherMenuTest {

    @Test
    @DisplayName("바우처를 선택할 때 1과 2를 입력 받는다.\n" +
            "이때 사용되는 selectVoucherMenu매서드가 제대로 동작하는지 검증하자.")
    void selectVoucherMenu() {
        //given
        String input = "1";

        //when
        VoucherMenu voucherMenu = VoucherMenu.selectVoucherMenu(input);

        //then
        assertEquals(VoucherMenu.FIXEDAMOUNTVOUCHER, voucherMenu);
    }
}