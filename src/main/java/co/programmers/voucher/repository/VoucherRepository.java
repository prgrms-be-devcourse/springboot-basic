package co.programmers.voucher.repository;

import java.util.List;

import co.programmers.voucher.dto.VoucherInquiryResponseDTO;
import co.programmers.voucher.entity.Voucher;

public interface VoucherRepository {

	void save(Voucher voucher);

	List<VoucherInquiryResponseDTO> findAll();
}
