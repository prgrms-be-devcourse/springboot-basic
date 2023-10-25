package devcourse.springbootbasic.exception;

public class InputException extends RuntimeException {

    private InputException(InputErrorMessage inputErrorMessage) {
        super(inputErrorMessage.getMessage());
    }

    public static InputException of(InputErrorMessage inputErrorMessage) {
        return new InputException(inputErrorMessage);
    }
}
