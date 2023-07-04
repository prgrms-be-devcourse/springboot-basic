package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.customer.dto.CustomerResDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResDto;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;

import java.util.List;

public interface Output {
    void printManagementMenu();

    void printCustomerCommand();

    void printVoucherCommand();

    void printDisplayVoucherPolicy();

    void printDisplayDiscountCondition(VoucherType policy);

    void printCustomers(CustomersResDto customers);

    void printCustomer(CustomerResDto customer);

    void printCreatedMsg(Voucher voucher);

    void printVoucherList(List<VoucherDto> voucher);

    void printBlackLists(CustomersResDto blacklists);

    void printFindEmpty();

    void printErrorMsg(String msg);

    void printNotImplementMsg();
}
