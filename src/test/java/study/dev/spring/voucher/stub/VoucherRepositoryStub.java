package study.dev.spring.voucher.stub;

import static study.dev.spring.voucher.fixture.VoucherFixture.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;

public class VoucherRepositoryStub implements VoucherRepository {

	@Override
	public void save(Voucher voucher) {

	}

	@Override
	public Optional<Voucher> findById(final UUID uuid) {
		return Optional.of(getFixedVoucher());
	}

	@Override
	public List<Voucher> findAll() {
		return List.of(getFixedVoucher(), getFixedVoucher());
	}
}
