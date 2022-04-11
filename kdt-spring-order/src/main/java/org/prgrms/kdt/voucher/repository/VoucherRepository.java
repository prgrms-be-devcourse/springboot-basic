package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class VoucherRepository {
    public HashMap<UUID, Voucher> voucherHashMap = new HashMap<UUID, Voucher>();

    public void insert(Voucher voucher){
        voucherHashMap.put(voucher.getVoucherId(), voucher);
    };
}
