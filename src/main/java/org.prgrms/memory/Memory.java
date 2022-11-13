package org.prgrms.memory;

import java.io.IOException;
import java.util.List;
import org.prgrms.voucher.voucherType.Voucher;

public interface Memory {

  Voucher save(Voucher voucher) throws IOException;

  List<String> findAll() throws IOException;
}
