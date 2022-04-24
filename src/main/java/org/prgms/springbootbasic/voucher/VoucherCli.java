package org.prgms.springbootbasic.voucher;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.service.VoucherServiceImpl;
import org.prgms.springbootbasic.voucher.vo.VoucherType;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import lombok.RequiredArgsConstructor;

@ShellComponent
@RequiredArgsConstructor
public class VoucherCli {

	private final VoucherServiceImpl voucherService;

	@ShellMethod(value = "create Voucher")
	public void create(String type, int value) {
		voucherService.createVoucher(type, value);
	}

	@ShellMethod("list Voucher")
	public String list() {
		StringBuilder builder = new StringBuilder();
		final Map<VoucherType, List<Voucher>> list = voucherService.list();
		list.keySet().stream()
			.forEach(key -> {
				builder.append(key.name() + "\n");
				list.get(key).stream()
					.forEach(v -> builder.append(v.toString() + "\n"));
			});

		return builder.toString();
	}

}
