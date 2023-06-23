package org.prgrms.kdt.controller.service;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.VoucherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	@Autowired
	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void createVoucher(VoucherDTO voucherDTO) {
		VoucherEntity voucherEntity = VoucherMapper.dtoToEntity(voucherDTO);
		voucherRepository.createVoucher(voucherEntity);
	}

	public List<VoucherDTO> getVouchers() {
		return voucherRepository.readAll()
			.stream()
			.map(VoucherMapper::entityToDTO)
			.collect(Collectors.toList());
	}
}
