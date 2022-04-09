package org.programmers.devcourse.voucher.engine.exception;

import java.util.Arrays;

public class ExceptionFormatter {

  private ExceptionFormatter() {
  }


  public static String formatExceptionForLogger(Exception e) {
    return e.getMessage() + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
        .reduce("", (e1, e2) -> e1 + "\n" + e2);
  }

}
