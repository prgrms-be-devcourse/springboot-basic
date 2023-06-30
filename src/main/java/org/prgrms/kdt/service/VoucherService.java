package org.prgrms.kdt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.controller.MainController;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final VoucherRepository voucherRepository;

	@Autowired
	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void createVoucher(VoucherDTO voucherDTO) {
		VoucherEntity voucherEntity = VoucherMapper.toVoucher(voucherDTO);
		voucherRepository.createVoucher(voucherEntity);
	}

	public List<VoucherDTO> getVouchers() {
		return voucherRepository.readAll()
			.stream()
			.map(VoucherMapper::toDTO)
			.collect(Collectors.toList());
	}
}
