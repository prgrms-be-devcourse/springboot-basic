package co.programmers.voucher_management.voucher.repository;

import java.io.IOException;
import java.util.List;

import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.entity.Voucher;

public interface VoucherRepository {

	void save(Voucher voucher) throws IOException;

	List<VoucherResponseDTO> findAll() throws Exception;

	int getVoucherCount() throws IOException;
}
