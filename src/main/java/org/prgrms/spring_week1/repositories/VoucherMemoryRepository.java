package org.prgrms.spring_week1.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.spring_week1.models.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {

    ConcurrentHashMap<UUID, Voucher> voucherHashMap = new ConcurrentHashMap<>();


    @Override
    public void insert(Voucher voucher) {
        voucherHashMap.put(voucher.getVoucherId(), voucher);
    }

//    @Override
//    public ConcurrentHashMap<UUID,Voucher> getAllVoucher() {
//            return voucherHashMap;
//    }

    @Override
    public List<String> getAllVoucher() {
        List<String> vouchers = new ArrayList<>();
        for (Voucher v : voucherHashMap.values()) {
            vouchers.add(v.toString());
        }
        return vouchers;
    }


}
