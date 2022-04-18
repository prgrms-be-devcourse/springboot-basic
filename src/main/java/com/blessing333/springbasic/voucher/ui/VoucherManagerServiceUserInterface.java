package com.blessing333.springbasic.voucher.ui;

import com.blessing333.springbasic.ui.ServiceUserInterface;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;

public interface VoucherManagerServiceUserInterface extends ServiceUserInterface {
    void showVoucherTypeSelectText();
    void printVoucherCreateSuccessMessage();
    VoucherCreateForm requestVoucherInformation();
}
