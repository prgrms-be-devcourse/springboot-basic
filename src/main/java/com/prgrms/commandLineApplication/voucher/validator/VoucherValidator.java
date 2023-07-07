package com.prgrms.commandLineApplication.voucher.validator;

import java.util.UUID;

public class VoucherValidator {

  private static final String INVALID_ID_ERROR = "Invalid ID";

  public static void checkId(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

}
