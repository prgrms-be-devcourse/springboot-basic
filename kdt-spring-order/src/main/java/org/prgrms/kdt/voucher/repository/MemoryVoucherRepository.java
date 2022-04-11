package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    public HashMap<UUID, Voucher> voucherHashMap = new HashMap<UUID, Voucher>();

    @Override
    public void insert(Voucher voucher){
        voucherHashMap.put(voucher.getVoucherId(), voucher);
    };
}
