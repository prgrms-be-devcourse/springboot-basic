package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.console.Command;

public record GetRequest(
        Command command
) implements Request {
}
