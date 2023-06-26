package co.programmers.voucher.service;

import co.programmers.voucher.dto.Response;
import co.programmers.voucher.dto.VoucherCreationRequestDTO;

public interface Launcher {
	Response run(VoucherCreationRequestDTO voucherCreationRequestDTO);
}
