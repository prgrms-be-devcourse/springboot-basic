package org.prgms.voucheradmin;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucheradmin.domain.administrator.Administrator;
import org.prgms.voucheradmin.domain.customer.dao.blacklist.BlackListRepository;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.prgms.voucheradmin.global.properties.VoucherAdminProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class VoucherAdminContextTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgms.voucheradmin"}
    )
    static class Config { }

    @Autowired
    ApplicationContext applicationContext;

    @Test
    @DisplayName("빈 생성 확인")
    void testBeanCreation() {
        Administrator administrator = applicationContext.getBean(Administrator.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
        BlackListRepository blackListRepository = applicationContext.getBean(BlackListRepository.class);
        VoucherAdminProperties voucherAdminProperties = applicationContext.getBean(VoucherAdminProperties.class);

        assertThat(administrator, notNullValue());
        assertThat(voucherService, notNullValue());
        assertThat(customerService, notNullValue());
        assertThat(voucherRepository, notNullValue());
        assertThat(blackListRepository, notNullValue());
        assertThat(voucherAdminProperties, notNullValue());
    }
}
