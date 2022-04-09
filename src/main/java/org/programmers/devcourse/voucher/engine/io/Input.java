package org.programmers.devcourse.voucher.engine.io;


import java.io.IOException;
import java.util.Optional;
import org.programmers.devcourse.voucher.engine.MenuSelection;
import org.programmers.devcourse.voucher.engine.voucher.VoucherMapper;

public interface Input extends AutoCloseable {

  Optional<MenuSelection> getSelection() throws IOException;

  VoucherMapper getVoucherMapper() throws IOException;

  long getVoucherDiscountData(VoucherMapper voucherMapper) throws IOException;
}
