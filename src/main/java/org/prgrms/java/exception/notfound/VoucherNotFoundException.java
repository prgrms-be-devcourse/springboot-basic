package org.prgrms.java.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class VoucherNotFoundException extends NotFoundException {
    public VoucherNotFoundException() {
        super("해당 바우처를 찾을 수 없습니다.");
    }
}
