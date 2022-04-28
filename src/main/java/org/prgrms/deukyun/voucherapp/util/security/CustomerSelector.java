package org.prgrms.deukyun.voucherapp.util.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@DependsOn("initData")
@RequiredArgsConstructor
public class CustomerSelector {

    private final CustomerService customerService;

    @PostConstruct
    public void setRandomCustomer() {
        List<Customer> customers = customerService.findAll();
        Customer customer = customers.get(RandomUtils.nextInt(0, customers.size()));

        CustomerHolder.setCustomer(customer);
    }
}
