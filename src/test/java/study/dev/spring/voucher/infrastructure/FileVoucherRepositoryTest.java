package study.dev.spring.voucher.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.dev.spring.common.stub.FileUtilsStub;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.fixture.VoucherFixture;

@DisplayName("[FileVoucherRepository Test] - Infrastructure Layer")
class FileVoucherRepositoryTest {

	private FileVoucherRepository fileVoucherRepository;

	@BeforeEach
	void setUp() {
		fileVoucherRepository = new FileVoucherRepository(List.of(new FileUtilsStub()), "hello.csv");
	}

	@Test
	@DisplayName("[바우처를 저장한다]")
	void saveTest() {
		//given
		Voucher voucher = VoucherFixture.getFixedVoucher();

		//when
		fileVoucherRepository.save(voucher);

		//then
		Optional<Voucher> actual = fileVoucherRepository.findById(voucher.getUuid());
		assertThat(actual).contains(voucher);
	}

	@Test
	@DisplayName("[아이디로 바우처를 조회한다]")
	void findByIdTest() {
		//given
		Voucher voucher = VoucherFixture.getFixedVoucher();
		fileVoucherRepository.save(voucher);

		//when
		Optional<Voucher> actual = fileVoucherRepository.findById(voucher.getUuid());

		//then
		assertThat(actual).contains(voucher);
	}

	@Test
	@DisplayName("[모든 바우처를 조회한다]")
	void findAllTest() {
		//given
		List<Voucher> vouchers = List.of(
			VoucherFixture.getFixedVoucher(),
			VoucherFixture.getFixedVoucher(),
			VoucherFixture.getFixedVoucher()
		);
		vouchers.forEach(fileVoucherRepository::save);

		//when
		List<Voucher> actual = fileVoucherRepository.findAll();

		//then
		assertThat(actual)
			.hasSameSizeAs(vouchers)
			.containsAll(vouchers);
	}

	@Test
	@DisplayName("[아이디로 바우처를 삭제한다]")
	void deleteById() {
		//given
		Voucher voucher = VoucherFixture.getFixedVoucher();
		fileVoucherRepository.save(voucher);

		//when
		fileVoucherRepository.deleteById(voucher.getUuid());

		//then
		Optional<Voucher> actual = fileVoucherRepository.findById(voucher.getUuid());
		assertThat(actual).isNotPresent();
	}
}