package org.programmers.devcourse.voucher.engine.io;


import java.io.IOException;
import org.programmers.devcourse.voucher.engine.MenuSelection;

public interface Input extends AutoCloseable {

  MenuSelection getSelection() throws IOException;

  String getVoucherTypeId() throws IOException;

  long getDiscountDegree(String voucherTypeId) throws IOException;

  void printInputError(String warningMessage);
}
