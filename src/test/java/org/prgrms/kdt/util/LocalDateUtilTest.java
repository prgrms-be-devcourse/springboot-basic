package org.prgrms.kdt.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by yhh1056
 * Date: 2021/09/11 Time: 5:24 오후
 */
class LocalDateUtilTest {

    @Test
    @DisplayName("날짜 변환 테스트")
    void parse() {
        String stringDateTime = "2020-01-01";
        LocalDate localDate = LocalDateUtil.parse(stringDateTime);

        assertThat(localDate.toString()).isEqualTo(stringDateTime);
        assertTrue(LocalDateUtil.isValid(stringDateTime));
    }

    @Test
    @DisplayName("날짜 변환 검증 테스트")
    void parseFail() {
        String stringDateTime = "0000-00-00";

        assertFalse(LocalDateUtil.isValid(stringDateTime));
    }
}