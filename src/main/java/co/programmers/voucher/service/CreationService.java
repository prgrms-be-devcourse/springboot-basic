package co.programmers.voucher.service;

import java.io.IOException;

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
	private static final Logger logger = LoggerFactory.getLogger(VoucherCommandLineRunner.class);
	private int voucherCnt;
	private final VoucherRepository repository;

	public CreationService(VoucherRepository repository) throws IOException {
		this.repository = repository;
		voucherCnt = repository.getVoucherCount();
	}

	public Response run(VoucherRequestDTO voucherRequestDTO) throws IOException {
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

	private int assignId() {
		return ++voucherCnt;
	}

}
