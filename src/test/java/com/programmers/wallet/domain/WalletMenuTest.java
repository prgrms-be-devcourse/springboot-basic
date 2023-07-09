package com.programmers.wallet.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class WalletMenuTest {

    @DisplayName("요청에 맞는 지갑 메뉴를 찾는다")
    @Test
    void findWalletMenu() {
        //given
        String input = "1";

        //when
        WalletMenu result = WalletMenu.findWalletMenu(input);

        //then
        assertThat(result, is(WalletMenu.ASSIGN_VOUCHER));
    }

    @DisplayName("요청한 지갑 메뉴 값이 비어있으면 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void findWalletMenuWithEmptyInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> WalletMenu.findWalletMenu(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("요청한 지갑 메뉴 값이 형식에 맞지 않으면 예외처리한다")
    @ValueSource(strings = { "ab", "*", "22", "c"})
    @ParameterizedTest
    void findMenuWithWrongInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> WalletMenu.findWalletMenu(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}