package com.programmers.voucher.configuration;

import com.programmers.voucher.CommandLineApplication;
import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.TextIoConsole;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.stream.VoucherStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public Console console() {
        return new TextIoConsole();
    }

    @Bean
    public CommandLineApplication commandLineApplication(Console console, VoucherStream voucherStream, VoucherFactory voucherFactory) {
        return new CommandLineApplication(console, voucherStream, voucherFactory);
    }

    @Bean
    public VoucherFactory voucherFactory(Console console, VoucherStream voucherStream) {
        return new VoucherFactory(console, voucherStream);
    }

}
