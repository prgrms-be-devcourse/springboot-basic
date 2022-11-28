package org.prgrms.springorder.service.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgrms.springorder.controller.dto.VoucherResponseDto;
import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.exception.NoSuchVoucherException;
import org.prgrms.springorder.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void createVoucher(VoucherType voucherType, double value) {
		Voucher voucher = VoucherFactory.createVoucher(voucherType, value);
		voucherRepository.save(voucher);
	}

	public List<VoucherResponseDto> getList() {
		return voucherRepository.findAll().stream().map(Voucher::toVoucherResponseDto).collect(Collectors.toList());
	}

	public VoucherResponseDto findById(UUID uuid) {
		return voucherRepository.findById(uuid)
			.orElseThrow(() -> new NoSuchVoucherException(ErrorMessage.NO_SUCH_VOUCHER_MESSAGE))
			.toVoucherResponseDto();
	}

	public void deleteById(UUID uuid) {
		voucherRepository.deleteById(uuid);
	}

}
