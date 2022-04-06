package org.programmers.devcourse.voucher.engine.io;

import java.util.Map;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;

public interface Output extends AutoCloseable {

  void print(String data);

  void printAllVouchers(Map<UUID, Voucher> voucherMap);


}
