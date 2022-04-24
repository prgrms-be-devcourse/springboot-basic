package org.prgrms.voucherapp.exception;

import lombok.AllArgsConstructor;

import java.sql.SQLException;

/*
* TODO : super 활용하도록 변경, overload 사용
* */
@AllArgsConstructor
public class WrongSqlValueException extends RuntimeException {
    private String message;

    @Override
    public String toString() {
        return message;
    }
}
