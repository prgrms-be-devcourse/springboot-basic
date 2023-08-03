package org.prgrms.kdt.service;

import org.prgrms.kdt.model.domain.Voucher;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.util.VoucherFactory;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

	private VoucherService voucherService;

	public Voucher findVoucherDomainById(Long id) {
		VoucherResponse voucherResponse = voucherService.findVoucherById(id);
		int amount = voucherResponse.amount();
		String voucherType = voucherResponse.voucherType();
		return VoucherFactory.createVoucherDomain(amount, voucherType);
	}
}
