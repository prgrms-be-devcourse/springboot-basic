package org.programmers.springbootbasic.io;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;

import java.io.IOException;

public interface Input {
    VoucherMainMenuCommand getVoucherMainMenuInput(String inputMessage) throws IOException;

}
