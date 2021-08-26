package org.prgrms.orderApp.infrastructure.library.console;

import org.prgrms.orderApp.domain.voucher.model.Voucher;

import java.util.List;

public interface Output {
    void showList(List<Object> listData);

    void infoMessage(String msg);

    void errorMessage(String msg);

    void print(String msg);



}
