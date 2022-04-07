package com.voucher.vouchermanagement.manager.io;

import java.io.IOException;

public interface VoucherManagerInput {
  String input(String prompt) throws IOException;
}
