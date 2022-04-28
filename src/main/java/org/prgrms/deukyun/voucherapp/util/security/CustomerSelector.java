package org.prgrms.deukyun.voucherapp.util.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 고객 선택자 <br>
 * - 로그인 기능을 대체하기 위해 구현 <br>
 * - 시스템이 램덤으로 초기 고객 데이터중 선택하여 로그인 한 것으로 처리
 */
@Component
@DependsOn("initDataConstructor")
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
