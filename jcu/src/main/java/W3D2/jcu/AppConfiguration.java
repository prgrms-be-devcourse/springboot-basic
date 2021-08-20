package W3D2.jcu;

import W3D2.jcu.voucher.VoucherRepository;
import W3D2.jcu.voucher.VoucherRepositoryImpl;
import W3D2.jcu.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepositoryImpl();
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

}
