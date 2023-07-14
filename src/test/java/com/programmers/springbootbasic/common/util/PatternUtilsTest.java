package com.programmers.springbootbasic.common.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PatternUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"20221130", "2022.05.31", "9999-9999-9999"})
    void given_비정상날짜포맷_when_날짜패턴매칭_then_성공(String wrongDate) {
        // given
        // when
        boolean result = PatternUtils.isDate(wrongDate);
        // then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-11-30 12:12", "2022-11-30 121212"})
    void given_비정상날짜시간포맷_when_날짜패턴매칭_then_성공(String wrongDate) {
        // given
        // when
        boolean result = PatternUtils.isDate(wrongDate);
        // then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"!@#$›$!‹$!@$!", "sangmin@kkk.12121", "dasdasgdfgs", "sasa@1.1"})
    void given_비정상이메일_when_이메일패턴매칭_then_실패(String wrongEmail) {
        // given
        // when
        boolean result = PatternUtils.isEmail(wrongEmail);
        // then
        assertThat(result).isFalse();
    }
}
