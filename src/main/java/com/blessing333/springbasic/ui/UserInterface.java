package com.blessing333.springbasic.ui;

import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;

public interface UserInterface {
    void showWelcomeText();
    void showVoucherTypeSelectText();
    void showHelpText();
    void printMessage(String message);
    void printVoucherCreateSuccess();
    VoucherCreateForm requestVoucherInformation();
    String inputMessage();
}
