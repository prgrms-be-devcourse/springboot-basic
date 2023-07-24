package org.prgrms.kdt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.CommonRuntimeException;
import org.prgrms.kdt.controller.MainController;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final VoucherRepository voucherRepository;

	public VoucherService(@Qualifier("JdbcVoucherRepository") VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void createVoucher(VoucherDTO voucherDTO) {
		VoucherEntity voucherEntity = voucherDTO.toEntity();
		voucherRepository.createVoucher(voucherEntity);
	}

	public VoucherDTO findVoucherById(Long voucherId) {
		try {
			VoucherEntity voucherEntity = voucherRepository.findById(voucherId);
			return VoucherFactory.getVoucherDTO(voucherEntity.getAmount(), voucherEntity.getVoucherType());
		} catch (RuntimeException ex){
			logger.error("NOT FOUND VOUCHER ID " + voucherId.toString());
			throw new CommonRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
		}
	}

	public List<VoucherDTO> getVouchers() {
		return voucherRepository.findAll()
			.stream()
			.map(voucherEntity -> {
					return voucherEntity.toEntity();
				}
			)
			.collect(Collectors.toList());
	}

	public void deleteVoucherById(Long voucherId) {
		voucherRepository.deleteById(voucherId);
	}
}
