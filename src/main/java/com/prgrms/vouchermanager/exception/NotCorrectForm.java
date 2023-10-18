package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

public class NotCorrectForm extends Exception {
    public NotCorrectForm() {
        super(ConsoleMessage.NOT_CORRECT_FORM.getMessage());
    }

}
