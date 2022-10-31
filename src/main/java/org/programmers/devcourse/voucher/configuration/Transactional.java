package org.programmers.devcourse.voucher.configuration;

public interface Transactional {

  void runTransaction(Runnable runnable);

}
