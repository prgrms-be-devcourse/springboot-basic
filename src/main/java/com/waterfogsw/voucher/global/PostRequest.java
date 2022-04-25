package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.console.Command;

public record PostRequest(
        Command command,
        String type,
        String value
) implements Request {
}
