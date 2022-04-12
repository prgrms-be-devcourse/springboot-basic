package org.prgrms.weeklymission.console;

import java.util.List;

public interface Output {
    void initMessage();
    void errorMessage(Exception e);
    <T> void printData(List<T> data);
}
