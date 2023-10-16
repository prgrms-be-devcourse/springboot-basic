package study.dev.spring.voucher.stub;

import java.util.List;

import study.dev.spring.voucher.application.VoucherService;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.fixture.VoucherFixture;

public class VoucherServiceStub extends VoucherService {

	public VoucherServiceStub() {
		super(null);
	}

	@Override
	public void createVoucher(final CreateVoucherRequest request) {

	}

	@Override
	public List<VoucherInfo> findAllVouchers() {
		return List.of(VoucherFixture.getVoucherInfo(), VoucherFixture.getVoucherInfo());
	}
}
