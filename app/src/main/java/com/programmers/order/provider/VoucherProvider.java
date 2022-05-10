package com.programmers.order.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.order.domain.VoucherType;
import com.programmers.order.exception.ServiceException;
import com.programmers.order.manager.VoucherClientManager;
import com.programmers.order.manager.VoucherInternalManager;

@Component
public class VoucherProvider {

	private final List<VoucherClientManager> clientManagers;

	private final List<VoucherInternalManager> internalManagers;

	public VoucherProvider(List<VoucherClientManager> clientManagers, List<VoucherInternalManager> internalManagers) {
		this.clientManagers = clientManagers;
		this.internalManagers = internalManagers;
	}

	public VoucherClientManager clientOf(VoucherType voucherType) {
		return clientManagers.stream()
				.filter(manager -> manager.getType() == voucherType)
				.findAny()
				.orElseThrow(() -> new ServiceException.NotSupportedException("지원하지 않는 바우처 타입입니다."));
	}

	public VoucherInternalManager internalOf(VoucherType voucherType) {
		return internalManagers.stream()
				.filter(manager -> manager.getType() == voucherType)
				.findAny()
				.orElseThrow(() -> new ServiceException.NotSupportedException("지원하지 않는 바우처 타입입니다."));
	}
}
