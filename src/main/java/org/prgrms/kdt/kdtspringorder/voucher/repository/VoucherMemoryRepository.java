package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 바우처 데이터에 대한 CRUD를 담당합니다. at Memory
 */
@Repository
@Profile({"local"})
public class VoucherMemoryRepository implements VoucherRepository{

    private static final Logger logger = LoggerFactory.getLogger(VoucherMemoryRepository.class);

    private Map<UUID,Voucher> voucherMap = new HashMap<>();

    @Override
    public List<Voucher> findAll() {
        logger.info("Access findAll()");
        return voucherMap.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        logger.info("Access findById()");
        logger.info("[Param] voucherId = " + voucherId);
        return Optional.ofNullable(voucherMap.get(voucherId));
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }

    @Override
    public UUID insert(Voucher voucher) {
        logger.info("Access insert()");
        logger.info("[Param] voucher = " + voucher.toString());
        this.voucherMap.put(voucher.getVoucherId(), voucher);
        return voucher.getVoucherId();
    }

    @Override
    public UUID updateDiscount(UUID voucherId, long amount) {
        return null;
    }

    @Override
    public UUID updateCustomerId(UUID voucherId, UUID customerId) {
        return null;
    }

    @Override
    public int delete(UUID voucherId) {
        return 0;
    }

}
