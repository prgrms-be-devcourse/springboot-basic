package com.voucher.vouchermanagement.manager.io;

import java.io.IOException;

public interface VoucherManagerOutput {
  void println(String string) throws IOException;

  void printMenu() throws IOException;

  void printVoucherType() throws IOException;
}
