package co.programmers.voucher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.programmers.voucher.dto.Response;
import co.programmers.voucher.dto.VoucherCreationRequestDTO;
import co.programmers.voucher.repository.VoucherRepository;

@Service
public class InquiryService implements Launcher {
	private final VoucherRepository voucherRepository;

	@Autowired
	private InquiryService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Override
	public Response run(VoucherCreationRequestDTO voucherCreationRequestDTO) {
		return Response.builder()
				.state(Response.State.SUCCESS)
				.responseData(voucherRepository.findAll())
				.build();
	}

}
