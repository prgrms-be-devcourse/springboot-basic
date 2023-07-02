package co.programmers.voucher_management.voucher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class VoucherCreationService {
	private static final Logger logger = LoggerFactory.getLogger(VoucherCreationService.class);
	private final VoucherRepository voucherRepository;

	public VoucherCreationService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Response run(VoucherRequestDTO voucherRequestDTO) {
		DiscountStrategy discountStrategy;
		String discountTypeName = voucherRequestDTO.getDiscountStrategy();
		int amount = voucherRequestDTO.getDiscountAmount();
		discountStrategy = DiscountTypeGenerator.of(discountTypeName, amount);
		Voucher voucher = new Voucher(discountStrategy);
		logger.info("Voucher created :discount type {}, amount {}", discountTypeName, amount);
		voucherRepository.save(voucher);
		return Response.builder()
				.state(Response.State.SUCCESS)
				.build();
	}


}
