package co.programmers.voucher_management.voucher.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.entity.Voucher;

@Repository
@Profile("local")
public class MemoryRepository implements VoucherRepository {
	private static final ArrayList<Voucher> repository = new ArrayList<>();
	private static final Logger logger = LoggerFactory.getLogger(MemoryRepository.class);

	private MemoryRepository() {
	}

	@Override
	public void save(Voucher voucher) {
		repository.add(voucher);
	}

	public List<VoucherResponseDTO> findAll() throws IOException {
		if (repository.isEmpty()) {
			throw new IOException("Empty repository");
		}
		List<VoucherResponseDTO> vouchers = new ArrayList<>();
		for (Voucher voucher : repository) {
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

	@Override
	public int getVoucherCount() {
		return repository.size();
	}

}
