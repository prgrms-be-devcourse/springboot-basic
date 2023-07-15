package com.prgrms.commandLineApplication.customer.validator;

import java.util.UUID;
import java.util.regex.Pattern;

public class CustomerValidator {

  private static final String REG_EXP_EMAIL = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(REG_EXP_EMAIL);

  private static final String INVALID_ID_ERROR = "Invalid ID";
  private static final String INVALID_NAME_ERROR = "Invalid Name";
  private static final String INVALID_EMAIL_ERROR = "Invalid Email";

  public static void checkId(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

  public static void checkName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException(INVALID_NAME_ERROR);
    }
  }

  public static void checkEmail(String email) {
    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException(INVALID_EMAIL_ERROR);
    }
  }

}
