package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.console.Command;

public record RequestMessage(
        Command command,
        String type,
        String value
) {
}
