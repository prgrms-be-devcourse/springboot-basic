package org.prgms.springbootbasic.voucher.service;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.io.Console;
import org.prgms.springbootbasic.voucher.io.Input;
import org.prgms.springbootbasic.voucher.io.Output;
import org.prgms.springbootbasic.voucher.repository.VoucherRepository;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
	private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	/**
	 * 원하는 Voucher 정보를 입력하고 create하는 메서드
	 *
	 * @param button
	 * @param VoucherParam
	 */
	public void create(String button, long VoucherParam) {
		VoucherType voucherType = VoucherType.getVoucherType(button);
		Voucher voucher = voucherType.createVoucher(VoucherParam);
		voucherRepository.save(voucher);
	}

	/**
	 * 생성된 리스트 전부를 보여주는 메서드
	 */
	public Map<String, List<Voucher>> list() {
		return voucherRepository.getVoucherListByType();
	}

}
