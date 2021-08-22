package org.prms.io;

import org.prms.domain.Voucher;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface Output {

    void cmdInit();
    void cmdError(String cmd);
    void cmdCorrect(String cmd);
//    void cmdList(ArrayList<Voucher> voucherList);
    void cmdList(ConcurrentHashMap<UUID,Voucher> voucherList);
    void cmdExit();


}
