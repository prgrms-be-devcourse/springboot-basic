package co.programmers.voucher.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import co.programmers.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher.entity.Voucher;

@Repository
@Component
public class MemoryVoucherRepository implements VoucherRepository {
	private static final ArrayList<Voucher> memoryRepository = new ArrayList<>();
	private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

	private MemoryVoucherRepository() {
	}

	@Override
	public void save(Voucher voucher) {
		memoryRepository.add(voucher);
	}

	public List<VoucherResponseDTO> findAll() {
		List<VoucherResponseDTO> vouchers = new ArrayList<>();
		for (Voucher voucher : memoryRepository) {
			logger.debug("Inquire voucher - id : {}, discount type : {}, amount : {}",
					voucher.getId(), voucher.getDiscountStrategy().getType(),
					voucher.getDiscountStrategy().getAmount());
			vouchers.add(VoucherResponseDTO.builder()
					.id(voucher.getId())
					.discountType(voucher.getDiscountStrategy().getType())
					.discountAmount(voucher.getDiscountStrategy().getAmount())
					.build());
		}
		return vouchers;
	}

}
