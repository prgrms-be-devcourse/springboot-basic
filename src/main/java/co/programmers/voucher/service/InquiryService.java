package co.programmers.voucher.service;

import org.springframework.stereotype.Service;

import co.programmers.voucher.dto.Response;
import co.programmers.voucher.repository.VoucherRepository;

@Service
public class InquiryService {
	private final VoucherRepository voucherRepository;

	private InquiryService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Response run() {
		return Response.builder()
				.state(Response.State.SUCCESS)
				.responseData(voucherRepository.findAll())
				.build();
	}

}
