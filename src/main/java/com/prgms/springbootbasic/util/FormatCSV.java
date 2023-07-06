package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.domain.Voucher;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FormatCSV {

    private static final String FORMAT_CSV = "%s,%s,%d\n";

    public String changeVoucherToCSV(Voucher voucher) {
        return String.format(FORMAT_CSV, voucher.getVoucherType(), voucher.getVoucherId(), voucher.getNumber());
    }

}
