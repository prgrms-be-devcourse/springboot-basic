package org.prgrms.dto;

import jakarta.validation.constraints.NotNull;

public record VoucherDTO(@NotNull String type, @NotNull long amount) { }
