package org.prgrms.vouchermanagement.exception;

public class InsertException extends RuntimeException {
  public InsertException() {
    super("DB에 데이터를 insert할 수 없습니다");
  }
}
