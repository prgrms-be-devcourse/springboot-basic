package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.wrapper.Username;

import java.util.UUID;

public record User(UUID userId, Username username) {
}
