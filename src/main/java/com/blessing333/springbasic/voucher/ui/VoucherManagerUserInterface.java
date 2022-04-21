package com.blessing333.springbasic.voucher.ui;

import com.blessing333.springbasic.common.ui.UserInterface;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;

public interface VoucherManagerUserInterface extends UserInterface {
    void showVoucherTypeSelectText();
    void printVoucherCreateSuccessMessage(Voucher voucher);
    void printVoucherInformation(Voucher voucher);
    VoucherCreateForm requestVoucherInformation();
}
