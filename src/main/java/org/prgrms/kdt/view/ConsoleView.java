package org.prgrms.kdt.view;

import java.text.MessageFormat;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherDTO;

public class ConsoleView implements InputView, OutputView {

	private final TextIO textIo;

	public ConsoleView() {
		this.textIo = TextIoFactory.getTextIO();
	}

	@Override
	public int getAmount() {
		return this.textIo.newIntInputReader()
			.withMinVal(0)
			.read("amount input: ");
	}

	@Override
	public Command getCommand() {
		String commandString = this.textIo.newStringInputReader()
			.withInputTrimming(true)
			.withMinLength(1)
			.read("command input: ");

		return Command.valueOf(commandString);
	}

	@Override
	public VoucherType getVoucherType() {
		int voucherTypeIdx = this.textIo.newIntInputReader()
			.withMinVal(1)
			.read("voucher type idx input: ");

		return VoucherType.valueOf(voucherTypeIdx);
	}

	@Override
	public void displayCommandGuideMessage() {
		System.out.println("=== Voucher Program ===");
		printCommandDescriptions();
	}

	private static void printCommandDescriptions() {
		Command[] commands = Command.values();
		for (Command command : commands) {
			String commandDescription = MessageFormat.format(
				"Type {0} {1}", command.name(), command.getDescription()
			);

			System.out.println(commandDescription);
		}
	}

	@Override
	public void displayExitMessage() {
		System.out.println("종료 되었습니다.");
	}

	@Override
	public void displayCreateVoucherMessage() {
		printVoucherIdxDescription();
		System.out.println("번호를 입력 해주세요: ");
	}

	private static void printVoucherIdxDescription() {
		VoucherType[] voucherTypes = VoucherType.values();
		for (VoucherType voucherType : voucherTypes) {
			String voucherIdxDescription = MessageFormat.format(
				"{0}: {1}", voucherType.name(), voucherType.getVoucherIdx()
			);
			System.out.println(voucherIdxDescription);
		}
	}

	@Override
	public void displayVoucherList(List<VoucherDTO> voucherDTOS) {
		for (VoucherDTO voucherDTO : voucherDTOS) {
			printVoucherDTO(voucherDTO);
		}
	}

	public void printVoucherDTO(VoucherDTO voucherDTO) {
		String voucherDataString = "voucher id: %d voucher type: %s voucher amount: %d".formatted(
			voucherDTO.getVoucherId(), voucherDTO.getVoucherType(), voucherDTO.getAmount()
		);

		System.out.println(voucherDataString);
	}
}
