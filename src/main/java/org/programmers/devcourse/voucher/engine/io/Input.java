package org.programmers.devcourse.voucher.engine.io;


import java.io.IOException;
import java.util.Optional;
import org.programmers.devcourse.voucher.engine.MenuSelection;
import org.programmers.devcourse.voucher.engine.voucher.Voucher.VoucherType;

public interface Input extends AutoCloseable {

  Optional<MenuSelection> getSelection() throws IOException;

  VoucherType getVoucherType() throws IOException;

  long getVoucherDiscountData(VoucherType voucherType) throws IOException;
}
