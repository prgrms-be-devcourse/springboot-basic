package com.programmers.voucher.config;

import com.programmers.voucher.stream.voucher.JdbcVoucherStream;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    private final DataSource dataSource;

    public JdbcConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    VoucherStream voucherStream() {
        return new JdbcVoucherStream(dataSource);
    }
}
