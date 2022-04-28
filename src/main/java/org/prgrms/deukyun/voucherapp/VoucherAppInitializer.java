package org.prgrms.deukyun.voucherapp;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Locale;

@Slf4j
@Component
public class VoucherAppInitializer {

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Faker faker;


    public VoucherAppInitializer(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.faker = new Faker(new Locale("ko"));
    }


    @PostConstruct
    public void initCustomers(){
        log.info("init customer insert");
        for (int i = 0; i < 100; i++) {
            customerService.insert(new Customer(faker.name().fullName(), i%2==0, Collections.emptyList()));
        }
    }
}
