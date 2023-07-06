package org.prgrms.vouchermission;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<Long, Voucher> storage = new ConcurrentHashMap<>();
    public static final AtomicLong voucherIdFactory = new AtomicLong(1);

    @Override
    public Voucher insert(Voucher voucher) {
        if (voucher.getVoucherId() == 0L) {
            Voucher completeVoucher = setRealVoucherId(voucher);
            return storage.put(completeVoucher.getVoucherId(), completeVoucher);
        }
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    private Voucher setRealVoucherId(Voucher voucher) {
        VoucherVO voucherVO = new VoucherVO(voucherIdFactory.getAndIncrement(), voucher.getValue(), voucher.getCreatedDate(), voucher.getExpirationDate());

        return VoucherFactory.valueOf(voucher.getVoucherType().name())
                .createVoucher(voucherVO);
    }
}
