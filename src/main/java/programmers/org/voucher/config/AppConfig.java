package programmers.org.voucher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import programmers.org.voucher.repository.JdbcVoucherRepository;
import programmers.org.voucher.repository.VoucherRepository;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public VoucherRepository voucherRepository(DataSource dataSource) {
        return new JdbcVoucherRepository(dataSource);
    }
}
