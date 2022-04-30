package org.prgrms.deukyun.voucherapp.system.initdata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

/**
 * 바우처 데이터 <br>
 * - 초기 바우처 데이터의 생성과 삽입을 책임짐
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherData {

    private final VoucherService voucherService;
    private final CustomerService customerService;

    private static final int AMOUNT_INIT_OWNED_VOUCHERS = 400;
    private static final int AMOUNT_INIT_NOT_OWNED_VOUCHERS = 100;

    public void initData() {
        initOwnedVouchers();
        initNotOwnedVouchers();
    }

    private void initOwnedVouchers() {
        List<UUID> customerIds = getCustomerIds();
        int customerAmount = customerIds.size();
        for (int i = 0; i < AMOUNT_INIT_OWNED_VOUCHERS; i++) {
            Voucher voucher;
            if (nextInt() % 2 == 0) {
                voucher = new FixedAmountDiscountVoucher(nextLong(1, 21) * 1000);
            } else {
                voucher = new PercentDiscountVoucher(nextLong(0, 11) * 10);
            }
            voucher.setOwnerId(customerIds.get(nextInt(0, customerAmount)));
            voucherService.insert(voucher);
        }
        log.info("{} init owned voucher inserted", AMOUNT_INIT_OWNED_VOUCHERS);
    }

    private List<UUID> getCustomerIds() {
        return customerService.findAll().stream().map(Customer::getId).collect(Collectors.toList());
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
