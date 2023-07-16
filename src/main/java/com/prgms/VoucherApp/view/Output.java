package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.domain.wallet.dto.WalletResponse;
import com.prgms.VoucherApp.domain.wallet.dto.WalletsResponse;

import java.util.List;

public interface Output {
    void printManagementMenu();

    void printCustomerCommand();

    void printVoucherCommand();

    void printWalletCommand();

    void printVoucherPolicy();

    void printDiscountCondition(VoucherType policy);

    void printCustomers(CustomersResponse customers);

    void printCustomer(CustomerResponse customer);

    void printVoucherList(List<VoucherResponse> voucher);

    void printVoucher(VoucherResponse voucher);

    void printBlackLists(CustomersResponse blacklists);

    void printFindEmpty();

    void printErrorMsg(String msg);

    void printNotImplementMsg();

    void printWallet(WalletResponse walletResponse);

    void printWallets(WalletsResponse walletsResponse);
}
