package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.helper.MessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputController {

    public void showVoucherList(List<Voucher> voucherList) {
        if(voucherList.isEmpty()) {
            MessageHelper.showVoucherListEmptyMessage();
            return;
        }
        voucherList.forEach(System.out::println);
    }

    public void showCustomerVoucherList(List<Customer> customerVoucherList) {
        if(customerVoucherList.isEmpty()) {
            MessageHelper.showBadCustomerListEmptyMessage();
            return;
        }
        customerVoucherList.forEach(System.out::println);
    }

}