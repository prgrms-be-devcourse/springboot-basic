package org.prgrms.vouchermanagement.exception;

public class UpdateException extends RuntimeException {
  public UpdateException() {
    super("DB의 데이터를 update 할 수 없습니다");
  }
}
