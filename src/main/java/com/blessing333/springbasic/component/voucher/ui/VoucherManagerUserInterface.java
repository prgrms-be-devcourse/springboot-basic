package com.blessing333.springbasic.component.voucher.ui;

import com.blessing333.springbasic.UserInterface;
import com.blessing333.springbasic.component.voucher.dto.VoucherCreateForm;

public interface VoucherManagerUserInterface extends UserInterface {

    void showVoucherTypeSelectText();
    void printVoucherCreateSuccessMessage();
    VoucherCreateForm requestVoucherInformation();

}
