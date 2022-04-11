package org.prgms.springbootbasic.voucher.service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.prgms.springbootbasic.voucher.io.Console;
import org.prgms.springbootbasic.voucher.io.Input;
import org.prgms.springbootbasic.voucher.io.Output;
import org.prgms.springbootbasic.voucher.repository.MemoryVoucherRepository;
import org.prgms.springbootbasic.voucher.repository.VoucherRepository;
import org.prgms.springbootbasic.voucher.vo.Voucher;

public class VoucherService {

	private final VoucherRepository voucherRepository;
	private final Input input;
	private final Output output;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
		input = new Console();
		output = new Console();
	}

	/**
	 * 원하는 Voucher 정보를 입력하고 create하는 메서드
	 *
	 * @param button
	 * @param VoucherParam
	 */
	public void create(String button, long VoucherParam) {
		output.printCreateVoucherManual();
		while (true) {
			try {
				VoucherType voucherType = VoucherType.getVoucherType(button);
				Voucher voucher = voucherType.createVoucher(VoucherParam);

				Voucher saved = voucherRepository.save(voucher);
				if (saved != null)
					break;
			} catch (IllegalArgumentException e) {
				output.print(e.getMessage());
			}
		}

	}

	/**
	 * 생성된 리스트 전부를 보여주는 메서드
	 */
	public void list() {
		Map<String, List<Voucher>> voucherListByType = voucherRepository.getVoucherListByType();
		StringBuilder builder = new StringBuilder("생성된 Voucher List\n");
		voucherListByType.keySet().stream()
			.forEach(s -> {
				builder.append(s + "\n");
				voucherListByType.get(s).stream()
					.forEach(v -> builder.append(v.toString() + "\n"));
			});

		output.print(builder.toString());
	}

}
