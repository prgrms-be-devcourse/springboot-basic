package study.dev.spring.voucher.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.domain.VoucherType;
import study.dev.spring.voucher.fixture.VoucherFixture;
import study.dev.spring.voucher.stub.VoucherRepositoryStub;

@DisplayName("[VoucherService Test] - Application Layer")
class VoucherServiceTest {

	private final VoucherService voucherService = new VoucherService(new VoucherRepositoryStub());

	@Test
	@DisplayName("[바우처를 생성한다]")
	void createVoucherTest() {
		//given
		CreateVoucherRequest request = VoucherFixture.getCreateRequest();

		//when
		Executable when = () -> voucherService.createVoucher(request);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[모든 바우처를 조회한다]")
	void findAllVouchersTest() {
		//when
		List<VoucherInfo> actual = voucherService.findAllVouchers();

		//then
		List<VoucherInfo> expected = List.of(
			new VoucherInfo("Fixed_Voucher", VoucherType.FIXED.getDescription(), 1000),
			new VoucherInfo("Fixed_Voucher", VoucherType.FIXED.getDescription(), 1000)
		);

		assertThat(actual)
			.hasSameSizeAs(expected)
			.containsAll(expected);
	}
}
