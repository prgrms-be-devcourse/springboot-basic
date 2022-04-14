package org.programmers.kdtspring.ConsoleIO;

import java.io.IOException;

public interface Output {

    void voucherCreated() throws IOException;

    void showAllVoucher() throws IOException;

    void showBlackList();

    void errorMessage();

}
