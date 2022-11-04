package org.prgrms.exception;

public class NoSuchMenuTypeException extends RuntimeException {

  public NoSuchMenuTypeException() {
    super("해당 키워드와 일치하는 프로그램이 없습니다.");
  }
}
