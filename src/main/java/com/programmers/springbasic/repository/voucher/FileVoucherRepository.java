package com.programmers.springbasic.repository.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.mapper.VoucherCsvFileMapper;
import com.programmers.springbasic.utils.FileUtils;

import jakarta.annotation.PostConstruct;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

	private final FileUtils fileUtils;
	private final VoucherCsvFileMapper voucherCsvFileMapper;
	private final Map<UUID, Voucher> storage;

	@Value("${file.voucher-path}")
	private String voucherFilePath;

	public FileVoucherRepository(FileUtils fileUtils, VoucherCsvFileMapper voucherCsvFileMapper) {
		this.fileUtils = fileUtils;
		this.voucherCsvFileMapper = voucherCsvFileMapper;
		this.storage = new ConcurrentHashMap<>();
	}

	@PostConstruct
	public void init() {
		List<String> fileLines = fileUtils.readFile(voucherFilePath);
		storage.putAll(this.voucherCsvFileMapper.linesToVoucherMap(fileLines));
	}

	@Override
	public Voucher insert(Voucher voucher) {
		storage.put(voucher.getVoucherId(), voucher);
		List<String> fileLines = voucherCsvFileMapper.voucherMapToLines(storage);
		fileUtils.writeFile(voucherFilePath, fileLines);
		return voucher;
	}

	@Override
	public Voucher update(Voucher voucher) {
		storage.put(voucher.getVoucherId(), voucher);
		List<String> fileLines = voucherCsvFileMapper.voucherMapToLines(storage);
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
		return Optional.ofNullable(storage.get(id));
	}

	@Override
	public void deleteById(UUID id) {
		storage.remove(id);
		List<String> fileLines = voucherCsvFileMapper.voucherMapToLines(storage);
		fileUtils.writeFile(voucherFilePath, fileLines);
	}

	@Override
	public List<Voucher> findAllById(List<UUID> voucherIds) {
		return voucherIds.stream()
			.map(storage::get)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

}
