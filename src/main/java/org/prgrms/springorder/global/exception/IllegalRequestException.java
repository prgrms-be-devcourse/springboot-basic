package org.prgrms.springorder.global.exception;

public class IllegalRequestException extends ClassCastException {

    public IllegalRequestException(Class<?> expected, Class<?> actual) {
        super(
            String.format(
                "invalid request class. class cast error. "
                    + "\n"
                    + "expected : %s, but actual : %s."
            , expected.getSimpleName(), actual.getSimpleName())
        );
    }

}
