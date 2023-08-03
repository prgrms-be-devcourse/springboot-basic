package org.prgrms.kdt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.CommonRuntimeException;
import org.prgrms.kdt.controller.MainController;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void saveVoucher(VoucherRequest voucherRequest) {
		VoucherEntity voucherEntity = VoucherFactory.createVoucherEntity(voucherRequest);
		voucherRepository.saveVoucher(voucherEntity);
	}

	public VoucherResponse findVoucherById(Long voucherId) {
		try {
			VoucherEntity voucherEntity = voucherRepository.findVoucherById(voucherId);
			return new VoucherResponse(voucherEntity.getVoucherId(), voucherEntity.getAmount(), voucherEntity.getVoucherType());
		} catch (RuntimeException ex){
			logger.error("NOT FOUND VOUCHER ID " + voucherId.toString());
			throw new CommonRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
		}
	}

	public List<VoucherResponse> getVouchers() {
		return voucherRepository.findAllEntities()
			.stream()
			.map(voucherEntity -> {
					Long id = voucherEntity.getVoucherId();
					int amount = voucherEntity.getAmount();
					String voucherType = voucherEntity.getVoucherType();
					return new VoucherResponse(id, amount, voucherType);
				}
			)
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
		voucherRepository.deleteVoucherById(voucherId);
	}
}
