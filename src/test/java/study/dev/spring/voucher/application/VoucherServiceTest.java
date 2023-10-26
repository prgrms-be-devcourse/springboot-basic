package study.dev.spring.voucher.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.fixture.VoucherFixture;
import study.dev.spring.wallet.domain.WalletRepository;
import study.dev.spring.wallet.fixture.WalletFixture;

@DisplayName("[VoucherService Test] - Application Layer")
class VoucherServiceTest {

	private final VoucherService voucherService;
	private final VoucherRepository voucherRepository;
	private final WalletRepository walletRepository;

	public VoucherServiceTest() {
		voucherRepository = Mockito.mock(VoucherRepository.class);
		walletRepository = Mockito.mock(WalletRepository.class);
		this.voucherService = new VoucherService(voucherRepository, walletRepository);
	}

	@Test
	@DisplayName("[바우처를 생성한다]")
	void createVoucherTest() {
		//given
		CreateVoucherRequest request = VoucherFixture.getCreateRequest();

		//when
		Executable when = () -> voucherService.createVoucher(request);

		//then
		assertDoesNotThrow(when);
		verify(voucherRepository, times(1)).save(any(Voucher.class));
	}

	@Test
	@DisplayName("[모든 바우처를 조회한다]")
	void findAllVouchersTest() {
		//given
		List<Voucher> vouchers = VoucherFixture.getVouchers();
		given(voucherRepository.findAll())
			.willReturn(vouchers);

		//when
		List<VoucherInfo> actual = voucherService.getAllVouchers();

		//then
		assertThat(actual).hasSameSizeAs(vouchers);
		for (int i = 0; i < actual.size(); i++) {
			assertVoucherInfo(actual.get(i), vouchers.get(i));
		}
	}

	@Test
	@DisplayName("[고객이 가지고 있는 바우처를 조회한다]")
	void getVouchersByCustomer() {
		//given
		String customerId = "customerId";
		List<Voucher> vouchers = VoucherFixture.getVouchers();

		given(walletRepository.findByCustomerId(customerId))
			.willReturn(WalletFixture.getWallets());
		given(voucherRepository.findByIds(anyList()))
			.willReturn(vouchers);

		//when
		List<VoucherInfo> actual = voucherService.getVouchersByCustomer(customerId);

		//then
		assertThat(actual).hasSameSizeAs(vouchers);
		for (int i = 0; i < actual.size(); i++) {
			assertVoucherInfo(actual.get(i), vouchers.get(i));
		}
	}

	private void assertVoucherInfo(VoucherInfo actual, Voucher expected) {
		assertAll(
			() -> assertThat(actual.voucherType()).isEqualTo(expected.getTypeDescription()),
			() -> assertThat(actual.name()).isEqualTo(expected.getName()),
			() -> assertThat(actual.discountAmount()).isEqualTo(expected.getDiscountAmount())
		);
	}
}
