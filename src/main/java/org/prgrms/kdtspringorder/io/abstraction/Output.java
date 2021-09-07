package org.prgrms.kdtspringorder.io.abstraction;

import java.util.List;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;

public interface Output {
  void printVoucherList(List<Voucher> listToPrint);
  void printVoucher(Voucher voucher);
  void print(String string);
}
