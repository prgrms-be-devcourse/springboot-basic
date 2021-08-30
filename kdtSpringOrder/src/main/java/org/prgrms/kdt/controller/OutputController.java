package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.prgrms.kdt.helper.MessageHelper.showBadCustomerListEmptyMessage;
import static org.prgrms.kdt.helper.MessageHelper.showVoucherListEmptyMessage;

@Component
public class OutputController {

    public void showVoucherList(List<Voucher> voucherList) {
        if(voucherList.isEmpty()) {
            showVoucherListEmptyMessage();
            return;
        }
        voucherList.forEach(System.out::println);
    }

    public void showCustomerVoucherList(List<Customer> customerVoucherList) {
        if(customerVoucherList.isEmpty()) {
            showBadCustomerListEmptyMessage();
            return;
        }
        customerVoucherList.forEach(System.out::println);
    }

}