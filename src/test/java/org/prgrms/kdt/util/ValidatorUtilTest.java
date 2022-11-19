package org.prgrms.kdt.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ValidatorUtilTest {

    @DisplayName("숫자가 아닌 값을 넣으면 false가 반환된다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "c", "  "})
    void 숫자_아닌_값넣기(String value) {
        assertThat(ValidatorUtil.isNumeric(value), is(false));
    }

    @DisplayName("숫자인 값을 넣으면 true가 반환된다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2.34", "-2"})
    void 숫자_값넣기(String value) {
        assertThat(ValidatorUtil.isNumeric(value), is(true));
    }
}