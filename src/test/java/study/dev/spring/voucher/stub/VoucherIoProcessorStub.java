package study.dev.spring.voucher.stub;

import java.util.List;

import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.fixture.VoucherFixture;
import study.dev.spring.voucher.presentation.utils.VoucherIoProcessor;

public class VoucherIoProcessorStub extends VoucherIoProcessor {

	public VoucherIoProcessorStub() {
		super(null, null, null);
	}

	@Override
	public CreateVoucherRequest inputCreateRequest() {
		return VoucherFixture.getCreateRequest();
	}

	@Override
	public void outputSuccessCreateMessage() {

	}

	@Override
	public void outputVoucherInfo(List<VoucherInfo> voucherInfos) {

	}
}
