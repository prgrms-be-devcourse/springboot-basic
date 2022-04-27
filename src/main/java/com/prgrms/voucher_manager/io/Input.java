package com.prgrms.voucher_manager.io;

import org.springframework.stereotype.Component;

import java.io.IOException;


public interface Input {

    String input (String s) throws IOException;

    String selectOption() throws IOException;
}
