package org.prgrms.deukyun.voucherapp.config;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

import static org.apache.commons.lang3.RandomUtils.*;

@Slf4j
@Component
public class InitialDataConstructor {

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Faker faker;

    private static final int AMOUNT_INIT_CUSTOMER = 100;
    private static final int AMOUNT_INIT_OWNED_VOUCHERS = 400;
    private static final int AMOUNT_INIT_NOT_OWNED_VOUCHERS = 100;

    public InitialDataConstructor(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.faker = new Faker(new Locale("ko"));
    }

    @PostConstruct
    public void initData() {
        UUID[] customerIds = initCustomers();
        initOwnedVouchers(customerIds);
        initNotOwnedVouchers();
    }

    private UUID[] initCustomers() {
        UUID[] customerIds = new UUID[AMOUNT_INIT_CUSTOMER];
        for (int i = 0; i < AMOUNT_INIT_CUSTOMER; i++) {
            Customer customer = new Customer(faker.name().fullName(), nextInt() % 2 == 0, Collections.emptyList());

            customerIds[i] = customer.getId();
            customerService.insert(customer);
        }
        log.info("{} init customer inserted", AMOUNT_INIT_CUSTOMER);
        return customerIds;
    }

    private void initOwnedVouchers(UUID[] customerIds) {
        for (int i = 0; i < AMOUNT_INIT_OWNED_VOUCHERS; i++) {
            Voucher voucher;
            if (nextInt() % 2 == 0) {
                voucher = new FixedAmountDiscountVoucher(nextLong(1, 21) * 1000);
            } else {
                voucher = new PercentDiscountVoucher(nextLong(0, 11) * 10);
            }
            voucher.setOwnerId(customerIds[nextInt(0, AMOUNT_INIT_CUSTOMER)]);
            voucherService.insert(voucher);
        }
        log.info("{} init owned voucher inserted", AMOUNT_INIT_OWNED_VOUCHERS);
    }

    private void initNotOwnedVouchers() {
        for (int i = 0; i < AMOUNT_INIT_NOT_OWNED_VOUCHERS; i++) {
            Voucher voucher;
            if (nextInt() % 2 == 0) {
                voucher = new FixedAmountDiscountVoucher(nextLong(1, 21) * 1000);
            } else {
                voucher = new PercentDiscountVoucher(nextLong(0, 11) * 10);
            }
            voucherService.insert(voucher);
        }
        log.info("{} init not owned voucher inserted", AMOUNT_INIT_NOT_OWNED_VOUCHERS);
    }

}
