package com.prgrms.commandLineApplication.voucher.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class VoucherValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(VoucherValidator.class);

  private static final String INVALID_ID_ERROR = "Invalid ID";

  public static void checkId(UUID id) {
    if (id == null) {
      LOGGER.error("Voucher Error Message => {}", INVALID_ID_ERROR);
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

}
