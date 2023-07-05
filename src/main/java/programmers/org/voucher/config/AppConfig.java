package programmers.org.voucher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import programmers.org.voucher.repository.MemoryVoucherRepository;
import programmers.org.voucher.repository.VoucherRepository;

@Configuration
public class AppConfig {

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }
}
