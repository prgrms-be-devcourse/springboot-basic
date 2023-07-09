package com.prgrms.commandLineApplication.customer.validator;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerValidator {

  private static final String REG_EXP_EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
  private static final Pattern EMAIL_PATTERN= Pattern.compile(REG_EXP_EMAIL);

  private static final String INVALID_ID_ERROR = "Invalid ID";
  private static final String INVALID_EMAIL_ERROR = "Invalid Email";

  public static void checkId(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

  public static void checkEmail(String email) {
    Matcher matchers = EMAIL_PATTERN.matcher(email);

    if (email == null || !matchers.matches()) {
      throw new IllegalArgumentException(INVALID_EMAIL_ERROR);
    }
  }

}
