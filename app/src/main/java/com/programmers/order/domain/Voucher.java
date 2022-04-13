package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
	UUID getVoucherId();

	LocalDateTime getCreated();

	String show();
}
