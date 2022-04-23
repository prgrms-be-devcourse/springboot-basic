package org.prgrms.springbootbasic.view;

import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.Menu;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

public interface View {

    void printMenu();

    Menu inputMenu();

    VoucherType selectVoucherType();

    int selectAmount();

    int selectPercent();

    void printList(List<Voucher> vouchers);

    void printCustomerBlackList();

    String selectName();

    String selectEmail();

    void printAllCustomers(List<Customer> customers);

    void printError(String message);

    UUID selectVoucherId();

    UUID selectCustomerId();

    void printCustomerVouchers(List<Voucher> customerVoucher);
}
