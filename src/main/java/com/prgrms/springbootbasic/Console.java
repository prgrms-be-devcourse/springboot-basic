package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.io.Input;
import com.prgrms.springbootbasic.io.Output;


public class Console implements Input, Output {

    @Override
    public String readCommand(String message) {
        return null;
    }

    @Override
    public String readString(String message) {
        return null;
    }

    @Override
    public void println(String message) {
        return;
    }
}
