package org.prgrms.kdt.common;

import org.prgrms.kdt.validate.VoucherFormValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yhh1056
 * Date: 2021/09/09 Time: 4:57 오후
 */

@Configuration
public class MvcConfig {

    @Bean
    public VoucherFormValidator voucherFormValidator() {
        return new VoucherFormValidator();
    }
}
