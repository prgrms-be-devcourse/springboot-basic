package org.prgrms.vouchermanagement.exception.validation;

public class AbnormalCustomerValueException extends RuntimeException {
    public AbnormalCustomerValueException() {
        super("올바르지 않은 Customer 입력값입니다." + System.lineSeparator() +
                "ID : 영문, 숫자, _만 가능" + System.lineSeparator() +
                "NAME : 영문, 한글, 공백만 가능 (30자)," + System.lineSeparator() +
                "EMAIL : 이메일 형식만 가능");
    }
}
