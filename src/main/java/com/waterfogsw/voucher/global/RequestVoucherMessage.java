package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.console.Command;

public record RequestVoucherMessage(
        Command command,
        String type,
        String value
) {
}
