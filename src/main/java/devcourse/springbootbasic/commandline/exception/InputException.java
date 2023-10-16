package devcourse.springbootbasic.commandline.exception;

public class InputException extends RuntimeException {

    public InputException(InputErrorMessage inputErrorMessage) {
        super(inputErrorMessage.getMessage());
    }
}
