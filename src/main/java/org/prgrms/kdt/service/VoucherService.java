package org.prgrms.kdt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.VoucherRuntimeException;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.domain.Voucher;
import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.IdGenerator;
import org.prgrms.kdt.util.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
	private final VoucherRepository voucherRepository;
	private final IdGenerator idGenerator;

	public VoucherService(VoucherRepository voucherRepository, IdGenerator idGenerator) {
		this.voucherRepository = voucherRepository;
		this.idGenerator = idGenerator;
	}

	public Long saveVoucher(VoucherRequest voucherRequest) {
		Long voucherId = idGenerator.getRandomId();
		VoucherEntity voucherEntity = new VoucherEntity(voucherId, voucherRequest.getAmount(), voucherRequest.getVoucherType());
		voucherRepository.saveVoucher(voucherEntity);

		return voucherId;
	}

	public VoucherResponse findVoucherById(Long voucherId) {
		VoucherEntity voucherEntity = voucherRepository.findVoucherById(voucherId)
		.orElseThrow(
			() -> {
				logger.error("NOT FOUND VOUCHER ID " + voucherId.toString());
				return new VoucherRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
			}
		);

		return VoucherResponse.from(voucherEntity);
	}

	public List<VoucherResponse> getVouchers() {
		return voucherRepository.findAllEntities()
			.stream()
			.map(VoucherResponse::from)
			.collect(Collectors.toList());
	}

	public List<VoucherResponse> findVoucherByVoucherType(VoucherType voucherType) {
		String voucherTypeString = voucherType.toString();

		return getVouchers()
			.stream()
			.filter(voucherResponse -> voucherResponse.voucherType().equals(voucherTypeString))
			.collect(Collectors.toList());
	}

	public void deleteVoucherById(Long voucherId) {
		VoucherResponse voucher = findVoucherById(voucherId);
		voucherRepository.deleteVoucherById(voucher.voucherId());
		logger.info("voucher id {} is deleted", voucherId);
	}
}
