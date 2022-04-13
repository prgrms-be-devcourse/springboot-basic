package org.prgrms.weeklymission.console;

import java.util.List;

public interface Output {
    void initMessage();
    void printVoucherOption();
    <T> void printData(List<T> data);
    void errorMessage(Exception e);
}
