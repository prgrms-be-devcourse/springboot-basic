package org.prgrms.weeklymission.console;

public interface Output {
    void initMessage();
    void saveSuccessMessage();
    void errorMessage(Exception e);
    void exitMessage();
    void printData(String vouchers);
    void createVoucherMessage();
}
