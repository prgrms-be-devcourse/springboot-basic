package co.programmers.voucher.repository;

import java.io.IOException;
import java.util.List;

import co.programmers.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher.entity.Voucher;

public interface VoucherRepository {

	void save(Voucher voucher) throws IOException;

	List<VoucherResponseDTO> findAll() throws Exception;

	int getVoucherCount() throws IOException;
}
