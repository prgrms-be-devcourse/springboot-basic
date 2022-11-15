package org.programmers.springbootbasic.io;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.dto.VoucherInputDto;

import java.io.IOException;

public interface Input {
    VoucherMainMenuCommand getVoucherMainMenuInput() throws IOException;

    VoucherInputDto getVoucherCreateMenuInput() throws IOException;
}
