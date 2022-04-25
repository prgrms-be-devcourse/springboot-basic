package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.console.Command;

public class PostRequest extends Request {

    private final String type;
    private final String value;

    public PostRequest(Command command, String type, String value) {
        super(command);
        this.type = type;
        this.value = value;
    }

    public String type() {
        return type;
    }

    public String value() {
        return value;
    }
}
