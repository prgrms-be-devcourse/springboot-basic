package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface Voucher {

	UUID getVoucherId();

	String getVoucherType();

	String getDiscountValue();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime getCreatedAt();

	String show();
}
