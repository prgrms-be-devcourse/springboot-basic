package co.programmers.voucher.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import co.programmers.voucher.dto.VoucherInquiryResponseDTO;
import co.programmers.voucher.entity.Voucher;

@Repository
@Component
public class MemoryVoucherRepository implements VoucherRepository {
	private static final ArrayList<Voucher> memoryRepository = new ArrayList<>();

	private MemoryVoucherRepository() {
	}

	@Override
	public void save(Voucher voucher) {
		memoryRepository.add(voucher);
	}

	public List<VoucherInquiryResponseDTO> findAll() {
		List<VoucherInquiryResponseDTO> vouchers = new ArrayList<>();
		for (Voucher voucher : memoryRepository) {
			vouchers.add(VoucherInquiryResponseDTO.builder()
					.id(voucher.getId())
					.name(voucher.getName())
					.description(voucher.getDescription())
					.discountType(voucher.getDiscountStrategy().getType())
					.discountAmount(voucher.getDiscountAmount())
					.createdAt(voucher.getCreatedAt())
					.expiredAt(voucher.getExpiredAt())
					.build());
		}
		return vouchers;
	}

}
