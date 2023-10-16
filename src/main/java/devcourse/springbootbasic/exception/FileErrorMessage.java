package devcourse.springbootbasic.exception;

import lombok.Getter;

@Getter
public enum FileErrorMessage {

    FILE_PATH_IS_NULL_OR_EMPTY("The file path cannot be null or empty."),
    CSV_FIELD_COUNT_MISMATCH("Field count mismatch."),
    INVALID_NUMBER_FORMAT("Invalid number format in the file."),
    INVALID_DATE_TIME_FORMAT("Invalid date-time format in the file."),
    IO_EXCEPTION("An error occurred during file input/output operations.");


    private final String message;

    FileErrorMessage(String message) {
        this.message = message;
    }
}
