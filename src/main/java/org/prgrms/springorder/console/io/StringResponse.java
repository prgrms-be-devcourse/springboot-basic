package org.prgrms.springorder.console.io;

public class StringResponse implements Response {

    private final String message;

    public StringResponse(Object object) {
        this.message = object.toString();
    }

    @Override
    public String getResponse() {
        return message;
    }

}
