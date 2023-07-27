package org.devcourse.springbasic.global.exception.custom;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateEmailException extends DataIntegrityViolationException {
    public DuplicateEmailException(String msg) {
        super(msg);
    }
}
