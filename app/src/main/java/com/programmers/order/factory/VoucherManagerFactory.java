package com.programmers.order.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.order.exception.NotSupportedException;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.VoucherType;

/**
 * comment : 굳이 빈으로 관리해야 하나 ? [고민...] := 스레드 세이프
 * 정적 팩토리 메소드 패턴 :
 */
@Component
public class VoucherManagerFactory {

	private final List<VoucherManager> managers;

	public VoucherManagerFactory(List<VoucherManager> managers) {
		this.managers = managers;
	}

	public VoucherManager getVoucherManager(VoucherType voucherType) {
		return managers.stream()
				.filter(manager -> manager.getType() == voucherType)
				.findAny()
				.orElseThrow(() -> new NotSupportedException(ErrorMessage.CLIENT_ERROR));
	}
}
