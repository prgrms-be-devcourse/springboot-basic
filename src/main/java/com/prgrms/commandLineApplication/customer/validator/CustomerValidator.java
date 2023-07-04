package com.prgrms.commandLineApplication.customer.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerValidator.class);

  private static final String EMAIL_FORMAT = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

  private static final String INVALID_ID_ERROR = "Invalid ID";
  private static final String INVALID_EMAIL_ERROR = "Invalid Email";

  public static void checkId(UUID id) {
    if (id == null) {
      LOGGER.error("Customer Error Message => {}", INVALID_ID_ERROR);
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

  public static void checkEmail(String email) {
    if (isEmailNull(email) || !isValidEmailFormat(email)) {
      LOGGER.error("Customer Error Message => {}", INVALID_EMAIL_ERROR);
      throw new IllegalArgumentException(INVALID_EMAIL_ERROR);
    }
  }

  private static boolean isEmailNull(String email) {
    boolean isNull = false;

    if (email == null) {
      isNull = true;
    }
    return isNull;
  }

  private static boolean isValidEmailFormat(String email) {
    boolean isValid = false;

    Pattern pattern = Pattern.compile(EMAIL_FORMAT);
    Matcher matchers = pattern.matcher(email);

    if (matchers.matches()) {
      isValid = true;
    }
    return isValid;
  }

}
