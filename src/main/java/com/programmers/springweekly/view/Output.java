package com.programmers.springweekly.view;

import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;

public interface Output {

    void outputProgramGuide();

    void outputSelectCreateVoucherGuide();

    void outputDiscountGuide();

    void outputExitMessage();

    void outputGetVoucherAll(VoucherListResponse voucherListResponse);


    void outputVoucherUpdateGuide();

    void outputGetCustomerList(CustomerListResponse customerList);

    void outputGetWalletList(WalletsResponse walletList);

    void outputGetWalletListByVoucher(WalletsResponse walletList);

    void outputGetWallet(WalletResponse wallet);

    void outputErrorMessage(String errorText);

    void outputCustomerUUIDGuide();

    void outputVoucherUUIDGuide();

    void outputCustomerUpdateGuide();

    void outputCompleteGuideContainMsg(String message);

    void outputCustomerCreateGuide();

    void outputCompleteGuide();

    void outputCustomerMenuGuide();

    void outputVoucherMenuGuide();

    void outputWalletMenuGuide();

    void outputWalletCustomerUUIDGuide();

    void outputWalletVoucherUUIDGuide();

    void outputWalletUUIDGuide();

    void outputWalletCustomerUUIDToFind();

    void outputWalletVoucherUUIDToFind();
}
