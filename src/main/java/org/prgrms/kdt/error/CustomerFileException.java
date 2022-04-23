package org.prgrms.kdt.error;

public class CustomerFileException extends RuntimeException {

    public CustomerFileException() {
        super(ErrorMessage.FILE_ERROR.getMessage());
    }

}
