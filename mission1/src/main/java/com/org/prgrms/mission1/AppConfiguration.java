package com.org.prgrms.mission1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
