package org.prgrms.kdt.utill.IO;

import org.prgrms.kdt.domain.Voucher;

import java.util.List;

public interface Output {

    void outputList(List<Voucher> voucherList);

    void outputListFile(List<String> list);
}
