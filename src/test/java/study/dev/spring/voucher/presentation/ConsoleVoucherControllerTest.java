package study.dev.spring.voucher.presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import study.dev.spring.voucher.stub.VoucherIoProcessorStub;
import study.dev.spring.voucher.stub.VoucherServiceStub;

@DisplayName("[ConsoleVoucherController Test] - Presentation Layer")
class ConsoleVoucherControllerTest {

	private final ConsoleVoucherController consoleVoucherController = new ConsoleVoucherController(
		new VoucherServiceStub(), new VoucherIoProcessorStub()
	);

	@Test
	@DisplayName("[바우처 생성 프로세스를 실행한다]")
	void createVoucherTest() {
		//when
		Executable when = consoleVoucherController::createVoucher;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[바우처 전체 조회 프로세스를 실행한다]")
	void findAllVouchersTest() {
		//when
		Executable when = consoleVoucherController::getAllVouchers;

		//then
		assertDoesNotThrow(when);
	}
}