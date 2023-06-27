package programmers.org.voucher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import programmers.org.voucher.io.Input;
import programmers.org.voucher.io.Output;
import programmers.org.voucher.io.VoucherConsole;

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
    public VoucherConsole console() {
        return new VoucherConsole(new Input(), new Output());
    }
}
