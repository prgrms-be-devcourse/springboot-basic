package devcourse.springbootbasic.exception;

public class FileException extends RuntimeException {

    private FileException(FileErrorMessage fileErrorMessage) {
        super(fileErrorMessage.getMessage());
    }

    public static FileException of(FileErrorMessage fileErrorMessage) {
        return new FileException(fileErrorMessage);
    }
}
