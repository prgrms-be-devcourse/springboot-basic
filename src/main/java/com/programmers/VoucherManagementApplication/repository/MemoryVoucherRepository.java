package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    //private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();


    @Override
    public Voucher addVoucher(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
        //logger.info("Add voucher => {}", voucher);
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        //logger.info("Call memory repository findAll method => {} ", vouchers);
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }
}
