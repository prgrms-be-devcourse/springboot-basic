package org.prgms.springbootbasic.voucher;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.service.VoucherService;
import org.prgms.springbootbasic.voucher.vo.VoucherType;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class VoucherCli {

	private final VoucherService voucherService;

	public VoucherCli(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@ShellMethod(value = "explain about service", key = {"start","menu"})
	public void printCreateVoucherManual() {
		StringBuilder builder = new StringBuilder();
		builder.append("생성하고 싶은 바우처를 선택하고 할인양 또는 할인율을 입력해주세요. (쉼표 ',' 를 사용하여 구분해주세요)\n");
		builder.append("1. Fixed Amount Voucher, 2. PercentDiscount Voucher\n");
		builder.append("ex) 입력 예시 create 1 1000 또는 create 2 20\n\n");

		builder.append("만약 생성된 모든 Voucher를 조회하고  싶으시다면 list를 입력해주세요\n");
		builder.append("ex) 임력 예시list\n");

		System.out.println(builder.toString());
	}

	@ShellMethod(value = "create Voucher")
	public void create(String button, int voucherParam) {
		voucherService.create(button, voucherParam);
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
