package com.programmers.springbasic.repository.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.utils.FileUtils;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository, InitializingBean {

	private final FileUtils fileUtils;
	private final Map<UUID, Voucher> storage;

	public FileVoucherRepository(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
		storage = new ConcurrentHashMap<>();
	}

	@Override
	public void afterPropertiesSet() {
		Map<UUID, Voucher> voucherMap = fileUtils.readVoucherFile();
		storage.putAll(voucherMap);
	}

	@Override
	public Voucher save(Voucher voucher) {
		storage.put(voucher.getVoucherId(), voucher);
		fileUtils.writeVoucherFile(storage);
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		List<Voucher> list = new ArrayList<>();
		storage.forEach((uuid, voucher) -> list.add(voucher));
		return list;
	}


}
