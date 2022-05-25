package org.devcourse.voucher.application.voucher.repository;

import org.devcourse.voucher.application.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> store = new LinkedHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    @Override
    public Voucher insert(Voucher voucher) {
        logger.info("Repository : Record a voucher write");
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        logger.info("Repository : Record a voucher update");
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Page<Voucher> findAll(Pageable pageable) {
        logger.info("Repository : Record a voucher read");
        List<Voucher> vouchers = new ArrayList<>();
        store.forEach(((uuid, voucher) -> vouchers.add(voucher)));
        int st = (int) pageable.getOffset();
        int ed = Math.min((st + pageable.getPageSize()), vouchers.size());

        return new PageImpl<>(
                vouchers.subList(st, ed), pageable, vouchers.size()
        );
    }

    @Override
    public void deleteAll() {
        logger.info("Repository : Record a voucher delete");
        store.clear();
    }

}
