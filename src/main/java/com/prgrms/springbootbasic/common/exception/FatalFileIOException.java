package com.prgrms.springbootbasic.common.exception;

public class FatalFileIOException extends RuntimeException {

  public FatalFileIOException(String message){
    super(message);
  }
}
