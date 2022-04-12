package org.prgms.springbootbasic.voucher.io;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output{
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public String enter() {
		return scanner.next();
	}

	@Override
	public void print(String content) {
		System.out.println(content);
	}

	@Override
	public void printProgramManual() {
		StringBuilder builder = new StringBuilder();
		builder.append("=== Voucher Program ===\n");
		builder.append("Type exit to exit the program.\n");
		builder.append("Type create to create a new voucher.\n");
		builder.append("Type list to list all vouchers.\n");

		print(builder.toString());

	}

	@Override
	public void printCreateVoucherManual() {
		StringBuilder builder = new StringBuilder();
		builder.append("생성하고 싶은 바우처를 선택하고 할인양 또는 할인율을 입력해주세요. (쉼표 ',' 를 사용하여 구분해주세요)\n");
		builder.append("1. Fixed Amount Voucher\n");
		builder.append("2. PercentDiscount Voucher\n");
		builder.append("ex) 입력 예시 create 1 1000 또는 create 2 20\n");

		print(builder.toString());
	}

	@Override
	public void printVoucherList(Map<String, List<Voucher>> voucherListByType) {
		StringBuilder builder = new StringBuilder("생성된 Voucher List\n");
		voucherListByType.keySet().stream()
			.forEach(s -> {
				builder.append(s + "\n");
				voucherListByType.get(s).stream()
					.forEach(v -> builder.append(v.toString() + "\n"));
			});

		print(builder.toString());
	}
}
