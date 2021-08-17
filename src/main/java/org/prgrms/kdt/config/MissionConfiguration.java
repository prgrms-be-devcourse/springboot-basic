package org.prgrms.kdt.config;

import org.prgrms.kdt.voucher.InMemoryVoucherRepository;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 1:30 오전
 */

@Configuration
public class MissionConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new InMemoryVoucherRepository();
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

}
