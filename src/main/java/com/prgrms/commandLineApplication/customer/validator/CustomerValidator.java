package com.prgrms.commandLineApplication.customer.validator;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerValidator {

  private static final String EMAIL_FORMAT = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
  private static final Pattern PATTERN = Pattern.compile(EMAIL_FORMAT);

  private static final String INVALID_ID_ERROR = "Invalid ID";
  private static final String INVALID_EMAIL_ERROR = "Invalid Email";

  public static void checkId(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

  public static void checkEmail(String email) {
    Matcher matchers = PATTERN.matcher(email);

    if (email == null || !matchers.matches()) {
      throw new IllegalArgumentException(INVALID_EMAIL_ERROR);
    }
  }

}
