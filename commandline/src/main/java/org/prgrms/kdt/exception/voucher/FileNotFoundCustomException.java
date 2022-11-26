package org.prgrms.kdt.exception.voucher;

import org.prgrms.kdt.exception.voucher.VoucherServerException;

public class FileNotFoundCustomException extends VoucherServerException {

    public FileNotFoundCustomException() {
    }

    public FileNotFoundCustomException(String message) {
        super(message);
    }
}
