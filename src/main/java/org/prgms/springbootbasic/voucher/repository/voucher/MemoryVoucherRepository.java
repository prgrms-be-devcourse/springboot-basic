package org.prgms.springbootbasic.voucher.repository.voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.prgms.springbootbasic.voucher.vo.VoucherType;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
	private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

	private final Map<UUID, Voucher> memory = new ConcurrentHashMap<>();

	/**
	 * Voucher를 memory에 저장하는 메서드
	 *
	 * @param voucher
	 * @return 저장된 Voucher
	 */
	@Override
	public UUID save(Voucher voucher) {
		Preconditions.checkArgument(voucher != null, "voucher은 null이면 안됩니다!");

		memory.put(voucher.getVoucherId(), voucher);
		logger.info(MessageFormat.format("Voucher가 저장되었습니다. voucher : {0}", voucher.toString()));
		return voucher.getVoucherId();
	}

	/**
	 * 저장된 Voucher들을 Voucher의 종류에 따라 조회하는 메서드
	 *
	 * @return Map<String, List < Voucher>>
	 */
	@Override
	public Map<VoucherType, List<Voucher>> getVoucherListByType() {
		return memory.values().stream()
			.collect(Collectors.groupingBy((voucher) -> voucher.getType()));
	}

	@Override
	public int getTotalVoucherCount() {
		return memory.size();
	}

	@Override
	public void deleteVouchers() {
		memory.clear();
	}

	@Override
	public Voucher findById(UUID voucherId) {
		return memory.entrySet().stream()
			.filter(e -> e.getKey().equals(voucherId))
			.map(e -> e.getValue())
			.findAny()
			.orElseThrow(() -> {
				throw new IllegalArgumentException(
					MessageFormat.format("존재하지 않는 ID를 입력하셨습니다! id : {0}", voucherId));
			});
	}

}
