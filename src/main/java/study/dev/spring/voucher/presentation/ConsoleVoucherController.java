package study.dev.spring.voucher.presentation;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.application.VoucherService;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;

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

	public void findAllVouchers() {
		List<VoucherInfo> vouchersInfos = voucherService.findAllVouchers();
		ioProcessor.outputVoucherInfo(vouchersInfos);
	}
}
