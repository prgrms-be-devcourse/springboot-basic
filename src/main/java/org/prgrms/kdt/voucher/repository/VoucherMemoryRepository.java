package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdt.voucher.domain.VoucherType.FIXED;
import static org.prgrms.kdt.voucher.domain.VoucherType.PERCENT;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(VoucherMemoryRepository.class);

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher save(VoucherDto voucherDto) {
        Voucher voucher = null;
        if(voucherDto.getType().equals(FIXED.getType())){
            voucher = FixedAmountVoucher.fromDto(voucherDto);
        }
        if(voucherDto.getType().equals(PERCENT.getType())){
            voucher = PercentDiscountVoucher.fromDto(voucherDto);
        }
        storage.put(voucher.getVoucherId(), voucher);
        logger.debug("바우처 생성 -> " + voucher.toString());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        logger.debug("모든 바우처의 갯수 : " + storage.size());
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(UUID voucherId) {

    }
}
