package org.prms.io;

import org.prms.domain.Voucher;

import java.util.ArrayList;

public interface Output {

    void cmdInit();
    void cmdError(String cmd);
    void cmdCorrect(String cmd);
    void cmdList(ArrayList<Voucher> voucherList);
    void cmdExit();


}
