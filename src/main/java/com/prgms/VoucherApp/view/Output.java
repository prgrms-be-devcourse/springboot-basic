package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.util.List;

public interface Output {
    void printManagementMenu();

    void printCustomerCommand();

    void printVoucherCommand();

    void printDisplayVoucherPolicy();

    void printErrorMsg(String msg);

    void printDisplayDiscountCondition(VoucherType policy);

    void printCustomers(CustomersResponse customers);

    void printCustomer(CustomerResponse customer);

    void printVoucherList(List<VoucherResponse> voucher);

    void printVoucher(VoucherResponse voucher);

    void printBlackLists(CustomersResponse blacklists);

    void printFindEmpty();

    void printNotImplementMsg();
}
