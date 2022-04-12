package org.prgrms.spring_week1.repositories;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.spring_week1.models.Voucher;

import java.util.UUID;

public interface VoucherRepository {
    void insert(Voucher voucher);
    //ConcurrentHashMap<UUID,Voucher> getAllVoucher();
    List<String> getAllVoucher();

}
