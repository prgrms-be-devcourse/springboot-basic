package co.programmers.voucher_management.voucher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.programmers.voucher_management.Response;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class CreationService {
	private static final Logger logger = LoggerFactory.getLogger(CreationService.class);
	private final VoucherRepository repository;
	private int voucherCnt;

	public CreationService(VoucherRepository repository) {
		this.repository = repository;
		voucherCnt = repository.getVoucherCount();
	}

	public Response run(VoucherRequestDTO voucherRequestDTO) {
		DiscountStrategy discountStrategy;
		String discountTypeName = voucherRequestDTO.getDiscountStrategy();
		int amount = voucherRequestDTO.getDiscountAmount();
		int id = assignId();
		discountStrategy = DiscountTypeGenerator.of(discountTypeName, amount);
		Voucher voucher = new Voucher(id, discountStrategy);
		logger.info("Voucher created : id {}, discount type {}, amount {}", id, discountTypeName, amount);
		repository.save(voucher);
		return Response.builder()
				.state(Response.State.SUCCESS)
				.build();
	}

	private int assignId() {
		return ++voucherCnt;
	}

}
