package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.Voucher;

import java.util.List;

public interface Output {

    void outputList(List<Voucher> voucherList);

   void outputList_file(List<String> list);

}
