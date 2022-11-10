package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    @Override
    public Voucher insert(UUID voucherID, Voucher voucher){
        storage.put(voucherID,voucher);
        logger.debug("insert Voucher = {}",voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherID){
        logger.debug("find Voucher = {}",voucherID);
        return Optional.ofNullable(storage.get(voucherID));
    }

    @Override
    public List<Voucher> findAll() {
        logger.debug("findAll Vouchers");
        return new ArrayList<>(storage.values());
    }

}
