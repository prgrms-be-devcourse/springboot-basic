package org.prgrms.kdt.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.prgrms.kdt.controller.MainController;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.VoucherFactory;
import org.prgrms.kdt.util.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final VoucherRepository voucherRepository;

	@Autowired
	public VoucherService(@Qualifier("JdbcVoucherRepository") VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void createVoucher(VoucherDTO voucherDTO) {
		VoucherEntity voucherEntity = VoucherMapper.toVoucherEntity(voucherDTO);
		voucherRepository.createVoucher(voucherEntity);
	}

	public Optional<VoucherDTO> findVoucherById(Long voucherId) {
		Optional<VoucherEntity> optionalVoucherEntity = voucherRepository.findById(voucherId);
		if (!optionalVoucherEntity.isPresent()) {
			logger.error("NOT FOUND VUCHER ID " + voucherId.toString());
			return Optional.empty();
		}
		VoucherEntity voucherEntity = optionalVoucherEntity.get();
		VoucherDTO voucherDTO = VoucherFactory.getVoucherDTO(voucherEntity.getAmount(), voucherEntity.getVoucherType());

		return Optional.of(voucherDTO);
	}

	public List<VoucherDTO> getVouchers() {
		return voucherRepository.findAll()
			.stream()
			.map(VoucherMapper::toVoucherDTO)
			.collect(Collectors.toList());
	}

	public boolean deleteVoucherById(Long voucherId) {
		return voucherRepository.deleteById(voucherId);
	}
}
