package org.programmers.devcourse.voucher.engine.io;


import java.io.IOException;
import java.util.Optional;
import org.programmers.devcourse.voucher.engine.MenuSelection;
import org.programmers.devcourse.voucher.engine.voucher.Voucher.Type;

public interface Input extends AutoCloseable {

  Optional<MenuSelection> getSelection() throws IOException;

  Type getVoucherType() throws IOException;

  long getVoucherDiscountData(Type voucherType) throws IOException;
}
