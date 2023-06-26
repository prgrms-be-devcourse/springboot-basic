package co.programmers.voucher.service;

import org.springframework.stereotype.Service;

import co.programmers.voucher.dto.Response;
import co.programmers.voucher.dto.VoucherCreationRequestDTO;
import co.programmers.voucher.entity.DiscountStrategy;
import co.programmers.voucher.entity.Voucher;
import co.programmers.voucher.repository.VoucherRepository;

@Service
public class CreationService implements Launcher {
	private static int voucherCnt;
	private final VoucherRepository repository;

	public CreationService(VoucherRepository repository) {
		this.repository = repository;
	}

	private static int assignId() {
		return ++voucherCnt;
	}

	@Override
	public Response run(VoucherCreationRequestDTO voucherCreationRequestDTO) {
		int amount = voucherCreationRequestDTO.getDiscountAmount();
		String discountType = voucherCreationRequestDTO.getDiscountStrategy();
		DiscountStrategy discountStrategy;
		try {
			discountStrategy = DiscountTypeGenerator.of(discountType, amount);
		} catch (IllegalArgumentException illegalArgumentException) {
			return Response.builder()
					.state(Response.State.FAILED)
					.responseData(illegalArgumentException.getMessage())
					.build();
		}
		int id = assignId();
		String name = voucherCreationRequestDTO.getName();
		String description = voucherCreationRequestDTO.getDescription();
		Voucher voucher = new Voucher(id, name, description, discountStrategy, amount);
		repository.save(voucher);
		return Response.builder()
				.state(Response.State.SUCCESS)
				.build();
	}

}
