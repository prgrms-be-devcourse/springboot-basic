package com.programmers.order.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.order.exception.NotSupportedException;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.VoucherType;

@Component
public class VoucherFactory {

	private final List<VoucherManager> managers;

	public VoucherFactory(List<VoucherManager> managers) {
		this.managers = managers;
	}

	public VoucherManager getVoucherManager(VoucherType voucherType) {
		return managers.stream()
				.filter(manager -> manager.getType() == voucherType)
				.findAny()
				.orElseThrow(() -> new NotSupportedException(ErrorMessage.CLIENT_ERROR));
	}
}
