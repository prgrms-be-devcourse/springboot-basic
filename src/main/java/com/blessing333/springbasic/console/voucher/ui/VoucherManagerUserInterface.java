package com.blessing333.springbasic.console.voucher.ui;

import com.blessing333.springbasic.console.ui.UserInterface;
import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.domain.voucher.model.Voucher;

public interface VoucherManagerUserInterface extends UserInterface {
    void printVoucherTypeGuide();
    void printRegisterComplete(Voucher voucher);
    void printVoucherInformation(Voucher voucher);
    VoucherCreateFormPayload requestVoucherInformation();
}
