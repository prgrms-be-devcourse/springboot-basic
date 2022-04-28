package org.prgrms.deukyun.voucherapp.util.initdata;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Locale;

import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * 고객 데이터 <br>
 * - 초기 고객 데이터의 생성과 삽입을 책임짐
 */
@Slf4j
@Component
public class CustomerData {

    private final CustomerService customerService;
    private final Faker faker;

    private static final int AMOUNT_INIT_CUSTOMER = 100;

    public CustomerData(CustomerService customerService) {
        this.customerService = customerService;
        this.faker = new Faker(new Locale("ko"));
    }

    public void initData() {
        initCustomers();
    }

    private void initCustomers() {
        for (int i = 0; i < AMOUNT_INIT_CUSTOMER; i++) {
            Customer customer = new Customer(faker.name().fullName(), nextInt() % 2 == 0, Collections.emptyList());

            customerService.insert(customer);
        }
        log.info("{} init customer inserted", AMOUNT_INIT_CUSTOMER);
    }
}
