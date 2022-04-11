package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VoucherRepository {
    private HashMap<String, Voucher> voucherRepository = new HashMap<String, Voucher>();

    public void insert(Voucher voucher){
        voucherRepository.put(voucher.getVoucherId().toString(), voucher);
    };
}
