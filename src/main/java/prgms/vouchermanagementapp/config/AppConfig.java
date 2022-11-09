package prgms.vouchermanagementapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prgms.vouchermanagementapp.controller.CommandExecutor;
import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.io.Reader;
import prgms.vouchermanagementapp.io.Writer;
import prgms.vouchermanagementapp.voucher.MemoryVouchers;
import prgms.vouchermanagementapp.voucher.VoucherCreator;
import prgms.vouchermanagementapp.voucher.VoucherManager;

@Configuration
public class AppConfig {

    @Bean
    public Reader reader() {
        return new Reader();
    }

    @Bean
    public Writer writer() {
        return new Writer();
    }

    @Bean
    public IOManager ioManager() {
        return new IOManager(reader(), writer());
    }

    @Bean
    public VoucherCreator voucherCreator() {
        return new VoucherCreator();
    }

    @Bean
    public MemoryVouchers memoryVouchers() {
        return new MemoryVouchers();
    }

    @Bean
    public VoucherManager voucherManager() {
        return new VoucherManager(voucherCreator(), memoryVouchers());
    }

    @Bean
    public CommandExecutor commandExecutor() {
        return new CommandExecutor(ioManager(), voucherManager());
    }
}
