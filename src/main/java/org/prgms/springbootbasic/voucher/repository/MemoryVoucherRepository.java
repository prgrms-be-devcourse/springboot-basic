package org.prgms.springbootbasic.voucher.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.prgms.springbootbasic.voucher.vo.Voucher;

import com.google.common.base.Preconditions;

public class MemoryVoucherRepository implements VoucherRepository{
	private final Map<UUID, Voucher> memory = new ConcurrentHashMap<>();

	@Override
	public Voucher save(Voucher voucher) {
		Preconditions.checkArgument(voucher != null, "voucher은 null이면 안됩니다!");

		memory.put(voucher.getVoucherId(), voucher);
		return voucher;
	}

	@Override
	public List<Voucher> getVoucherList() {
		return memory.entrySet().stream()
			.map(e -> e.getValue())
			.collect(Collectors.toList());
	}
}
