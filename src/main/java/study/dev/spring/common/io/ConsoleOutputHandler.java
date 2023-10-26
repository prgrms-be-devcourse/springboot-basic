package study.dev.spring.common.io;

import java.io.PrintStream;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputHandler implements OutputHandler {

	private static final PrintStream out = System.out;
	private static final String NEW_LINE = System.lineSeparator();

	@Override
	public void showSystemMessage(final String message) {
		out.println(message);
	}

	@Override
	public void showStartMessage() {
		String message = "=== Voucher Program ===" + NEW_LINE
						 + "종료 : exit" + NEW_LINE
						 + "바우처 생성 : create_voucher" + NEW_LINE
						 + "모든 바우처 조회 : all_vouchers" + NEW_LINE
						 + "특정 고객이 지닌 바우처 조회 : vouchers_by_customer" + NEW_LINE
						 + "블랙리스트 고객 조회 : black_list" + NEW_LINE
						 + "모든 고객 조회 : all_customer" + NEW_LINE
						 + "특정 바우처를 지닌 고객 조회 : customers_by_voucher" + NEW_LINE
						 + "지갑 추가 : add_wallet" + NEW_LINE
						 + "특정 고객의 바우처 지갑 삭제 : remove_wallet_by_customer" + NEW_LINE;

		out.println(message);
	}
}
