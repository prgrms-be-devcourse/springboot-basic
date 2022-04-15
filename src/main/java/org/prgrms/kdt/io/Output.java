package org.prgrms.kdt.io;

public interface Output {
    void printMessage(String message);
    void printCommandManual();
    void printInvalidCommand();
    void printShutDownSystem();
    void printVoucherManual();
    void printVoucherValue();
    void printVoucherCreateSuccess(String voucherInfo);
}
