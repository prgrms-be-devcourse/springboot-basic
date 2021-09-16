package com.prgms.missionW3D2;

import com.prgms.missionW3D2.voucher.VoucherRepository;
import com.prgms.missionW3D2.voucher.VoucherRepositoryImpl;
import com.prgms.missionW3D2.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepositoryImpl();
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }
}
