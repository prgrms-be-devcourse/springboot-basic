package programmers.org.voucher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import programmers.org.voucher.controller.VoucherController;
import programmers.org.voucher.io.ConsoleService;
import programmers.org.voucher.repository.MemoryVoucherRepository;
import programmers.org.voucher.repository.VoucherRepository;
import programmers.org.voucher.service.VoucherService;

@Configuration
public class AppConfig {

    @Bean
    public VoucherController voucherController() {
        return new VoucherController(voucherService(), consoleService());
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public ConsoleService consoleService() {
        return new ConsoleService();
    }
}
