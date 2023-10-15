package study.dev.spring.voucher.infrastructure;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherType;

@DisplayName("[MemoryVoucherRepository Test] - Infrastructure Layer")
class MemoryVoucherRepositoryTest {

	private final MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

	@Test
	@DisplayName("[바우처를 메모리에 저장한다]")
	void saveTest() {
		//given
		Voucher voucher = Voucher.createVoucher(VoucherType.FIXED, "voucher", 1000);

		//when
		memoryVoucherRepository.save(voucher);

		//then
		Voucher expected = memoryVoucherRepository.findById(voucher.getUuid()).orElseThrow();
		assertThat(voucher).isEqualTo(expected);
	}

	@Test
	@DisplayName("[바우처를 id 로 조회한다]")
	void findByIdTest() {
		//given
		Voucher voucher = Voucher.createVoucher(VoucherType.FIXED, "voucher", 1000);
		memoryVoucherRepository.save(voucher);

		//when
		Voucher actual = memoryVoucherRepository.findById(voucher.getUuid()).orElseThrow();

		//then
		assertThat(actual).isEqualTo(voucher);
	}
}
