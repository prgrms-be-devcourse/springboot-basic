package co.programmers.voucher.repository;

import java.util.List;

import co.programmers.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher.entity.Voucher;

public interface VoucherRepository {

	void save(Voucher voucher);

	List<VoucherResponseDTO> findAll();
}
