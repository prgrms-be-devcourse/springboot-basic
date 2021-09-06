package org.prgrms.orderApp.util.library.console;

import java.util.List;

public interface Output {
    void showList(List listData);

    void infoMessage(String msg);

    void errorMessage(String msg);

    void print(String msg);
}
