package org.programmers.spbw1.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {
    private static final int FIXED_MAX_VALUE = 100000;
    @Test
    @DisplayName("get range test")
    void getRangeTest(){
        String fixedRange = VoucherType.getRange(VoucherType.FIXED);
        String percentRange = VoucherType.getRange(VoucherType.PERCENT);

        assertThat(fixedRange).isEqualTo("1~100,000");
        assertThat(percentRange).isEqualTo("1~100");
    }

    @Test
    @DisplayName("valid range test")
    void validRangeTest(){
        Random r = new Random();

        boolean outRangeUnderFixed = VoucherType.validRange(VoucherType.FIXED, (long) -r.nextInt());
        boolean outRangeOverFixed = VoucherType.validRange(VoucherType.FIXED, (long) r.nextInt(100) + FIXED_MAX_VALUE + 1);

        boolean outRangeUnderPercent = VoucherType.validRange(VoucherType.PERCENT, (long) -r.nextInt());
        boolean outRangeOverPercent = VoucherType.validRange(VoucherType.PERCENT, (long) r.nextInt(100) + 101);

        boolean inRangeFixed = VoucherType.validRange(VoucherType.FIXED, (long) r.nextInt(FIXED_MAX_VALUE));
        boolean inRangePercent = VoucherType.validRange(VoucherType.PERCENT, (long) r.nextInt(100));

        assertThat(outRangeOverPercent).isFalse();
        assertThat(outRangeOverFixed).isFalse();
        assertThat(outRangeUnderPercent).isFalse();
        assertThat(outRangeUnderFixed).isFalse();

        assertThat(inRangeFixed).isTrue();
        assertThat(inRangePercent).isTrue();
    }

    @Test
    @DisplayName("simple toString test")
    void toStringTest(){
        String fixedToString = VoucherType.FIXED.toString();
        String percentToString = VoucherType.PERCENT.toString();

        assertThat(fixedToString).isEqualTo("FIXED");
        assertThat(percentToString).isEqualTo("PERCENT");
    }
}