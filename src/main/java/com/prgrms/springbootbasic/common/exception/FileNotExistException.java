package com.prgrms.springbootbasic.common.exception;

public class FileNotExistException extends RuntimeException{
  public FileNotExistException(String message){
    super(message);
  }

}
