package com.prgrms.voucher_manager.io;

import org.springframework.stereotype.Component;

import java.io.IOException;


public interface Output {

    void wrongInput() throws IOException;

    void consoleMenu() throws IOException;

    void selectVoucher() throws IOException;

    void exitProgram() throws IOException;

    void emptyVoucherRepository() throws IOException;

}
