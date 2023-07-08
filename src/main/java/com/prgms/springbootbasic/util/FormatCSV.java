package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.domain.Voucher;
import org.springframework.stereotype.Component;

@Component
public class FormatCSV {

    private static final String FORMAT_CSV = "%s,%s,%d\n";

    public String changeVoucherToCSV(Voucher voucher) {
        return String.format(FORMAT_CSV, voucher.getVoucherType(), voucher.getVoucherId(), voucher.getNumber());
    }

}
