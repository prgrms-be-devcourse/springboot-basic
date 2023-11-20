package org.prgrms.vouchermanager.exception;

public class FailToDeleteException extends RuntimeException implements CustomException{
    public FailToDeleteException() {
        super("삭제에 실패했습니다");
    }

    public FailToDeleteException(String message) {
        super(message);
    }
}
