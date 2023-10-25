package study.dev.spring.voucher.stub;

import static study.dev.spring.voucher.fixture.VoucherFixture.*;

import java.util.List;
import java.util.Optional;

import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;

public class VoucherRepositoryStub implements VoucherRepository {

	@Override
	public Voucher save(Voucher voucher) {
		return voucher;
	}

	@Override
	public Optional<Voucher> findById(final String uuid) {
		return Optional.of(getFixedVoucher());
	}

	@Override
	public List<Voucher> findAll() {
		return List.of(getFixedVoucher(), getFixedVoucher());
	}

	@Override
	public void deleteById(String uuid) {

	}
}
