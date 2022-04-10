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

	/**
	 * Voucher를 memory에 저장하는 메서드
	 *
	 * @param voucher
	 * @return 저장된 Voucher
	 */
	@Override
	public Voucher save(Voucher voucher) {
		Preconditions.checkArgument(voucher != null, "voucher은 null이면 안됩니다!");

		memory.put(voucher.getVoucherId(), voucher);
		return voucher;
	}

	/**
	 * 저장된 Voucher들을 조회하는 메서드
	 *
	 * @return Voucher 리스트
	 */
	@Override
	public List<Voucher> getVoucherList() {
		return memory.entrySet().stream()
			.map(e -> e.getValue())
			.collect(Collectors.toList());
	}
}
