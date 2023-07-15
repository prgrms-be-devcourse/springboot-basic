package org.prgrms.kdt.model.repository.file;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.util.FileIO;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.jshell.spi.ExecutionControl;

@Repository
@Qualifier("FileVoucherRepository")
public class FileVoucherRepository implements VoucherRepository {

	private final ObjectMapper objectMapper;
	private final FileIO fileIO;
	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	public FileVoucherRepository(ObjectMapper objectMapper, FileIO fileIO) {
		this.objectMapper = objectMapper;
		this.fileIO = fileIO;
	}

	@Override
	public VoucherEntity createVoucher(VoucherEntity voucherEntity) {
		try {
			String voucherJson = objectMapper.writeValueAsString(voucherEntity);
			fileIO.saveStringToFile(voucherJson + System.lineSeparator());
		} catch (JsonProcessingException e) {
			logger.error("save 메서드에서 voucher 저장 실패");
			logger.error(e.toString());
		}

		return voucherEntity;
	}

	@Override
	public List<VoucherEntity> findAll() {
		String fileAllText = fileIO.loadStringFromFile();
		List<VoucherEntity> voucherEntities = toVoucherEntities(fileAllText);
		return voucherEntities;
	}

	private List<VoucherEntity> toVoucherEntities(String fileAllText) {
		List<VoucherEntity> voucherEntities = new ArrayList<>();

		Arrays.stream(fileAllText.split(System.lineSeparator()))
			.forEach(line -> {
				try {
					VoucherEntity voucherEntity = objectMapper.readValue(line, VoucherEntity.class);
					voucherEntities.add(voucherEntity);
				} catch (JsonProcessingException e) {
					logger.error("readAll 메서드에서 파일 불러오기 실패");
					logger.error(e.toString());
				}
			});
		return voucherEntities;
	}

	@Override
	public VoucherEntity updateVoucher(VoucherEntity voucherEntity) {
		throw new UnsupportedOperationException("아직 미 구현한 기능 입니다.");
	}

	@Override
	public Optional<VoucherEntity> findById(Long vouherId) {
		String fileAllText = fileIO.loadStringFromFile();
		List<VoucherEntity> voucherEntities = toVoucherEntities(fileAllText);
		return voucherEntities
			.stream()
			.filter(voucherEntity -> voucherEntity.getVoucherId() == vouherId)
			.findFirst();
	}

	@Override
	public boolean deleteById(Long voucherId) {
		throw new UnsupportedOperationException("아직 미 구현한 기능 입니다.");
	}
}
