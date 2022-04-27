package com.prgrms.voucher_manager.io;

import java.io.IOException;


public interface Output {

    void wrongInput() throws IOException;

    void consoleMenu() throws IOException;

    void createVoucher() throws IOException;

    void exitProgram() throws IOException;

    void voucherMenu() throws IOException;

    void customerMenu() throws IOException;

    void ListCustomerByVoucherType() throws IOException;

    void walletMenu() throws IOException;
}
