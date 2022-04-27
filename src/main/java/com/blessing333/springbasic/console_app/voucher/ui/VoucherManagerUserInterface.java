package com.blessing333.springbasic.console_app.voucher.ui;

import com.blessing333.springbasic.console_app.ui.UserInterface;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateFormPayload;

public interface VoucherManagerUserInterface extends UserInterface {
    void printVoucherTypeGuide();
    void printRegisterComplete(Voucher voucher);
    void printVoucherInformation(Voucher voucher);
    VoucherCreateFormPayload requestVoucherInformation();
}
