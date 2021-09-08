package org.prgrms.kdtspringdemo.common;

import org.prgrms.kdtspringdemo.console.VoucherOperator;
import org.prgrms.kdtspringdemo.voucher.repository.FileVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "org.prgrms.kdtspringdemo",
        "org.prgrms.kdtspringdemo.console",
})
public class Config {
    @Bean
    public FileVoucherRepository fileVoucherRepository() {
        return new FileVoucherRepository();
    }

    @Autowired
    FileVoucherRepository fileVoucherRepository;

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(fileVoucherRepository);
    }

    @Bean
    public VoucherOperator voucherOperator(VoucherService voucherService) {
        return new VoucherOperator(voucherService);
    }
}
