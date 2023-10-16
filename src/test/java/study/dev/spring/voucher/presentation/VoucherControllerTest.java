package study.dev.spring.voucher.presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import study.dev.spring.voucher.stub.VoucherIoProcessorStub;
import study.dev.spring.voucher.stub.VoucherServiceStub;

@DisplayName("[VoucherController Test] - Presentation Layer")
class VoucherControllerTest {

	private final VoucherController voucherController = new VoucherController(
		new VoucherServiceStub(), new VoucherIoProcessorStub()
	);

	@Test
	@DisplayName("[바우처 생성 프로세스를 실행한다]")
	void createVoucherTest() {
		//when
		Executable when = voucherController::createVoucher;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[바우처 전체 조회 프로세스를 실행한다]")
	void findAllVouchersTest() {
		//when
		Executable when = voucherController::findAllVouchers;

		//then
		assertDoesNotThrow(when);
	}
}