package org.prgrms.voucherapp.io;

public interface Output {
    void introMessage();
    void errorMessage(String msg);
    void exitMessage();
    void informVoucherTypeFormat();
}
