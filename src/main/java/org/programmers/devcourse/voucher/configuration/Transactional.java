package org.programmers.devcourse.voucher.configuration;

public interface Transactional {

  public void runTransaction(Runnable runnable);

}
