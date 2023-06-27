package kr.co.programmers.school.voucher.domain.voucher.exception;

public class ApplicationException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public ApplicationException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}