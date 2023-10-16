package devcourse.springbootbasic.exception;

public class FileException extends RuntimeException {

    public FileException(FileErrorMessage fileErrorMessage) {
        super(fileErrorMessage.getMessage());
    }
}
