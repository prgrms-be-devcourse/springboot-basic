package com.programmers.voucher.config;

import com.programmers.voucher.stream.voucher.MemoryVoucherStream;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.context.annotation.Bean;

public class MemoryConfig {

    @Bean
    public VoucherStream voucherStream() {
        return new MemoryVoucherStream();
    }

}
