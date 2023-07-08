package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.domain.FixedAmountVoucher;
import com.prgms.springbootbasic.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class FormatCSVTest {

    @Test
    void 바우처를_CSV_포맷으로_변경() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        FormatCSV formatCSV = new FormatCSV();

        // when
        String csv = formatCSV.changeVoucherToCSV(voucher);

        // then
        assertThat(csv).isEqualTo(String.format("%s,%s,%d\n", voucher.getVoucherType(), voucher.getVoucherId(), voucher.getNumber()));
    }

}