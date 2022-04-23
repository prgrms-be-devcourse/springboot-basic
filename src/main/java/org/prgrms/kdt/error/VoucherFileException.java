package org.prgrms.kdt.error;

public class VoucherFileException extends RuntimeException {

    public VoucherFileException() {
        super(ErrorMessage.FILE_ERROR.getMessage());
    }

}
