package study.dev.spring.voucher.stub;

import java.util.List;
import java.util.UUID;

import study.dev.spring.voucher.application.VoucherService;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.fixture.VoucherFixture;

public class VoucherServiceStub extends VoucherService {

	public VoucherServiceStub() {
		super(null, null);
	}

	@Override
	public String createVoucher(final CreateVoucherRequest request) {
		return UUID.randomUUID().toString();
	}

	@Override
	public List<VoucherInfo> getAllVouchers() {
		return List.of(VoucherFixture.getVoucherInfo(), VoucherFixture.getVoucherInfo());
	}
}
