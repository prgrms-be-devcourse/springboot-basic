package prgms.springbasic;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prgms.springbasic.repository.MemoryVoucherRepository;
import prgms.springbasic.repository.VoucherRepository;
import prgms.springbasic.voucher.VoucherService;
import prgms.springbasic.voucher.VoucherServiceImpl;

@Configuration
public class CommandLineAppConfig {
    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherServiceImpl(voucherRepository());
    }
}
