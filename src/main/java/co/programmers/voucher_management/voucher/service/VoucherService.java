package co.programmers.voucher_management.voucher.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
	private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Response create(VoucherRequestDTO voucherRequestDTO) {
		DiscountStrategy discountStrategy;
		String discountTypeName = voucherRequestDTO.getDiscountStrategy();
		int amount = voucherRequestDTO.getDiscountAmount();
		discountStrategy = DiscountTypeGenerator.of(discountTypeName, amount);
		Voucher voucher = new Voucher(discountStrategy);
		logger.info("Voucher created :discount type {}, amount {}", discountTypeName, amount);
		voucherRepository.create(voucher);
		return Response.builder()
				.state(Response.State.SUCCESS)
				.build();
	}

	public Response inquiry() {
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
