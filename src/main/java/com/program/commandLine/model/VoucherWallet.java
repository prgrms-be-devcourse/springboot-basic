package com.program.commandLine.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherWallet(UUID voucherId, LocalDateTime create_at) {

}
