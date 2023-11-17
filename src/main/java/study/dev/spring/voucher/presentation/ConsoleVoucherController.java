package study.dev.spring.voucher.presentation;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.application.VoucherService;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.presentation.utils.VoucherIoProcessor;

@Component
@RequiredArgsConstructor
public class ConsoleVoucherController {

	private final VoucherService voucherService;
	private final VoucherIoProcessor ioProcessor;

	public void createVoucher() {
		CreateVoucherRequest request = ioProcessor.inputCreateRequest();
		voucherService.createVoucher(request);
		ioProcessor.outputSuccessCreateMessage();
	}

	public void getAllVouchers() {
		List<VoucherInfo> vouchersInfos = voucherService.getAllVouchers();
		ioProcessor.outputVoucherInfo(vouchersInfos);
	}

	public void getVouchersByCustomer() {
		String customerId = ioProcessor.inputCustomerId();

		List<VoucherInfo> result = voucherService.getVouchersByCustomer(customerId);
		ioProcessor.outputVoucherInfo(result);
	}
}
