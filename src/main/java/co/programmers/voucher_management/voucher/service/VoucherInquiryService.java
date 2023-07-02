package co.programmers.voucher_management.voucher.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class VoucherInquiryService {
	private final VoucherRepository voucherRepository;

	private VoucherInquiryService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Response run() {
		List<VoucherResponseDTO> voucherResponseDTOs;
		try {
			List<Voucher> inquiredData = voucherRepository.findAll();
			voucherResponseDTOs = inquiredData.stream()
					.map(VoucherResponseDTO::new)
					.collect(Collectors.toList());
		} catch (RuntimeException runtimeException) {
			return new Response(Response.State.FAILED, runtimeException.getMessage());
		}
		return Response.builder()
				.state(Response.State.SUCCESS)
				.responseData(voucherResponseDTOs)
				.build();
	}
}
