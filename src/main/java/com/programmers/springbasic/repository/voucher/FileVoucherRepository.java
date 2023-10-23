package com.programmers.springbasic.repository.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.mapper.VoucherMapper;
import com.programmers.springbasic.utils.FileUtils;

import jakarta.annotation.PostConstruct;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

	private final FileUtils fileUtils;
	private final VoucherMapper voucherMapper;
	private final Map<UUID, Voucher> storage;

	@Value("${file.voucher-path}")
	private String voucherFilePath;

	public FileVoucherRepository(FileUtils fileUtils, VoucherMapper voucherMapper) {
		this.fileUtils = fileUtils;
		this.voucherMapper = voucherMapper;
		this.storage = new ConcurrentHashMap<>();
	}

	@PostConstruct
	public void init() {
		List<String> fileLines = fileUtils.readFile(voucherFilePath);
		storage.putAll(this.voucherMapper.linesToVoucherMap(fileLines));
	}

	@Override
	public Voucher save(Voucher voucher) {
		storage.put(voucher.getVoucherId(), voucher);
		List<String> fileLines = voucherMapper.voucherMapToLines(storage);
		fileUtils.writeFile(voucherFilePath, fileLines);
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		List<Voucher> list = new ArrayList<>();
		storage.forEach((uuid, voucher) -> list.add(voucher));
		return list;
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		return Optional.empty();
	}

	@Override
	public void deleteById(UUID id) {

	}

}
