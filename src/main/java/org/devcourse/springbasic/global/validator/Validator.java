package org.devcourse.springbasic.global.validator;

public interface Validator<T> {
    boolean validate(T valueToVerify);
}
