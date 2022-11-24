package org.programmers.springbootbasic.domain.console;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;

public interface Input {
    VoucherMainMenuCommand getVoucherMainMenuInput();

    VoucherInputDto getVoucherCreateMenuInput();
}
