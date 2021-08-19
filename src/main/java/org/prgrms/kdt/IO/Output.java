package org.prgrms.kdt.IO;

public interface Output {
    void voucherCreateSuccess();
    void voucherCreateFail();
    void exit();

    void inputError(String message);
}
