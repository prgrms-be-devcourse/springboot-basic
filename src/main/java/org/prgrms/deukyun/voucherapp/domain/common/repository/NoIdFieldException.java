package org.prgrms.deukyun.voucherapp.domain.common.repository;

/**
 * 메모리 리포지토리에서 Id 필드가 id란 이름으로 정의되지 않은 경우 발생
 */
public class NoIdFieldException extends RuntimeException {

    public NoIdFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
