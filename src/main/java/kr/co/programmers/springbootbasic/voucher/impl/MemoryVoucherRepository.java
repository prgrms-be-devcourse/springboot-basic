package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//@Primary
//@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private final Map<UUID, Voucher> repository;

    public MemoryVoucherRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        logger.info("MemoryVoucherRepository에서 ID가 {}인 바우처를 조회합니다...", voucherId);
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public List<Voucher> listAll() {
        logger.info("MemoryVoucherRepository에서 모든 바우처를 조회합니다...");
        return repository.values()
                .stream()
                .toList();
    }

    @Override
    public void save(UUID voucherId, Voucher voucher) {
        logger.info("MemoryVoucherRepository에 바우처를 저장합니다...");
        repository.put(voucherId, voucher);
    }
}
