package org.prgrms.kdt.utill.IO;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherEntity;

import java.util.List;

public interface Output {

    void outputList(List<VoucherEntity> voucherList);

    void outputListFile(List<String> list);
}
