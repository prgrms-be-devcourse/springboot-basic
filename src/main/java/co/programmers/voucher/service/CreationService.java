package co.programmers.voucher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.programmers.voucher.controller.VoucherCommandLineRunner;
import co.programmers.voucher.dto.Response;
import co.programmers.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher.entity.DiscountStrategy;
import co.programmers.voucher.entity.Voucher;
import co.programmers.voucher.repository.VoucherRepository;

@Service
public class CreationService {
	private static final Logger logger = LoggerFactory.getLogger(CreationService.class);
	private static int voucherCnt;
	private final VoucherRepository repository;

	public CreationService(VoucherRepository repository) {
		this.repository = repository;
	}

	private static int assignId() {
		return ++voucherCnt;
	}

	public Response run(VoucherRequestDTO voucherRequestDTO) {
		DiscountStrategy discountStrategy;
		String discountType = voucherRequestDTO.getDiscountStrategy();
		int amount = voucherRequestDTO.getDiscountAmount();
		try {
			discountStrategy = DiscountTypeGenerator.of(discountType, amount);
		} catch (IllegalArgumentException illegalArgumentException) {
			logger.debug("Voucher creation error : wrong type of discount");
			return Response.builder()
					.state(Response.State.FAILED)
					.responseData(illegalArgumentException.getMessage())
					.build();
		}
		int id = assignId();
		Voucher voucher = new Voucher(id, discountStrategy);
		repository.save(voucher);
		logger.debug("Voucher created : id[{}], discount type[{}], amount[{}], ", id, discountType, amount);
		return Response.builder()
				.state(Response.State.SUCCESS)
				.build();
	}

}
