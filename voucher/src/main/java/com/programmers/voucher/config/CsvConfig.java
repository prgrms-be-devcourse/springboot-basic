package com.programmers.voucher.config;

import com.programmers.voucher.stream.voucher.CsvVoucherStream;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.context.annotation.Bean;

public class CsvConfig {

    @Bean
    public VoucherStream voucherStream() {
        return new CsvVoucherStream();
    }
}
