package org.programmers.springbootbasic.io;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.dto.VoucherInputDto;

public interface Input {
    VoucherMainMenuCommand getVoucherMainMenuInput();

    VoucherInputDto getVoucherCreateMenuInput();
}
