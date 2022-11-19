package org.programmers.springbootbasic.domain.voucher.repository;

import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.exception.NotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    @Override
    public Voucher save(Voucher voucher) {
        logger.info("voucher memoryRepository에 저장 voucher = {}", voucher);
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("voucher 전체 조회");
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Voucher voucher) {
        throw new NotSupportedException("지원하지 않는 기능입니다.");
    }

    @Override
    public void deleteAll() {
        throw new NotSupportedException("지원하지 않는 기능입니다.");
    }

    /**
     * @param voucherId
     */
    @Override
    public void deleteById(UUID voucherId) {
        throw new NotSupportedException("지원하지 않는 기능입니다.");
    }
}
