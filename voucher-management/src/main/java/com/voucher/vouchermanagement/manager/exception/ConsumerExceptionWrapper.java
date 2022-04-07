package com.voucher.vouchermanagement.manager.exception;

import java.util.function.Consumer;

public class ConsumerExceptionWrapper {
  public static <T> Consumer<T> throwingConsumerWrapper(
      ThrowingConsumer<T, Exception> throwingConsumer) {

    return i -> {
      try {
        throwingConsumer.accept(i);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    };
  }
}
