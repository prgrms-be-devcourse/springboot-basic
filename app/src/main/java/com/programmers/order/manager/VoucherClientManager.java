package com.programmers.order.manager;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.controller.dto.VoucherDto;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;

public interface VoucherClientManager {

	Voucher create(VoucherDto.Create createDto);

	VoucherType getType();

	Voucher update(VoucherDto.Update updateDto);
}
