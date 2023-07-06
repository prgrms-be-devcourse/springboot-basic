package kr.co.programmers.springbootbasic.io;


import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;

import java.util.List;

public interface Output {
    void printProgramMenu();

    void printVoucherCreateMenu();

    void printAmountEnterMessage(VoucherType voucherType);

    void printMessage(String message);

    void printVoucherMessage(VoucherResponse dto);

    void printCustomerMessage(CustomerResponse dto);

    void printVoucherListMessage(List<VoucherResponse> list);

    void printCustomerListMessage(List<CustomerResponse> list);

    void printExit();

    void printCustomerServiceMenu();

    void printCustomerCreateMessage();

    void printCustomerFindMenu();

    void printWalletServiceMenu();

    void printNoResult();

    void printCustomerUuidTypeMessage();

    void printTypeCustomerStatus();

    void printVoucherUuidTypeMessage();

    void printWalletUuidTypeMessage();

    void printWalletSaveMessage(WalletSaveDto responseDto);

    void printWalletFindMessage(WalletResponse walletResponse);

    void printVoucherServiceMenu();
}
