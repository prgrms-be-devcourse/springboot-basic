package org.programmers.springbootbasic.domain.voucher.repository;

import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.exception.NotFoundException;
import org.programmers.springbootbasic.exception.NotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
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
    public Optional<Voucher> findById(UUID voucherId) {
        if(store.containsKey(voucherId)) return Optional.ofNullable(store.get(voucherId));
        else throw new NotFoundException("해당 Voucher를 찾을 수 없습니다.");
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        throw new NotSupportedException("지원하지 않는 기능입니다.");
    }

    @Override
    public void update(Voucher voucher) {
        throw new NotSupportedException("지원하지 않는 기능입니다.");
    }

    @Override
    public void deleteAll() {
        throw new NotSupportedException("지원하지 않는 기능입니다.");
    }

    @Override
    public void deleteById(UUID voucherId) {
        throw new NotSupportedException("지원하지 않는 기능입니다.");
    }
}
