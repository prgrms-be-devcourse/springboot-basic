package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private final Map<UUID, Voucher> repository;

    public MemoryVoucherRepository() {
        this.repository = new HashMap<>();
    }

    @Override
    public List<Voucher> listAll() {
        logger.info("MemoryVoucherRepository에서 바우처 조회를 시작합니다...");
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
