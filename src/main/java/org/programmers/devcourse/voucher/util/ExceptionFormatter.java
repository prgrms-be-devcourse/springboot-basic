package org.programmers.devcourse.voucher.util;

import java.util.Arrays;

public class ExceptionFormatter {

  public static String formatExceptionForLogger(Exception e) {
    return e.getMessage() + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
        .reduce("", (e1, e2) -> e1 + "\n" + e2);
  }

}
