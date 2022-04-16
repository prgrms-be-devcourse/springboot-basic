package org.programmers.devcourse.voucher.engine.io;

import java.util.Collection;
import java.util.List;
import org.programmers.devcourse.voucher.engine.blacklist.BlackList;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

public interface Output extends AutoCloseable {

  void print(String data);

  void printAllVouchers(Collection<Voucher> vouchers);


  void printBlackList(List<BlackList> list);
}
