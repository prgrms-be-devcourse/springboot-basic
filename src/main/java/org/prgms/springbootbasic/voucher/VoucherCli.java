package org.prgms.springbootbasic.voucher;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.service.VoucherService;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class VoucherCli {

	private final VoucherService voucherService;

	public VoucherCli(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@ShellMethod("create Voucher")
	public void create(String button, long voucherParam) {
		voucherService.create(button, voucherParam);
	}

	@ShellMethod("list Voucher")
	public String list() {
		StringBuilder builder = new StringBuilder();
		final Map<String, List<Voucher>> list = voucherService.list();
		list.keySet().stream()
			.forEach(key -> {
				builder.append(key + "\n\n");
				list.get(key).stream()
					.forEach(v -> builder.append(v.toString() + "\n"));
			});

		return builder.toString();
	}

}
