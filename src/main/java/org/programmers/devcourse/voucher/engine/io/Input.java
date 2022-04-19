package org.programmers.devcourse.voucher.engine.io;


import java.io.IOException;
import org.programmers.devcourse.voucher.engine.MenuSelection;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;

public interface Input extends AutoCloseable {

  MenuSelection getSelection() throws IOException;

  String getVoucherTypeId() throws IOException;

  long getVoucherDiscountData(VoucherType voucherType) throws IOException;

  void printInputError(String warningMessage);
}
