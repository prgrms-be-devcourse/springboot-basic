package org.prgms.voucheradmin.global.exception.customexception.fileexception;

public class FileWriteException extends FileException{
    public FileWriteException() {
        super("file write exception");
    }

    public FileWriteException(String message) {
        super(message);
    }
}
