package org.prgms.voucheradmin.global.exception.customexception.fileexception;

public class FileReadException extends FileException{
    public FileReadException() {
        super("file read exception");
    }

    public FileReadException(String message) {
        super(message);
    }
}
