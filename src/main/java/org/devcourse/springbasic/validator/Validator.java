package org.devcourse.springbasic.validator;

public interface Validator<T> {
    boolean validate(T valueToVerify);
}
