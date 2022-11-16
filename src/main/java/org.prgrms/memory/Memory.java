package org.prgrms.memory;

import java.util.List;
import org.prgrms.voucher.voucherType.Voucher;

public interface Memory {

  Voucher save(Voucher voucher);

  List<Voucher> findAll();
}
