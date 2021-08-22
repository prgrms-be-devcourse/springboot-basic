package org.prms.repository;

import org.prms.domain.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface VoucherRepository {

    //Optional --> Null값 방지
    Optional<Voucher> findById(UUID voucherId);

    void insert(UUID vocherId,Voucher voucher);
//    void insert(Voucher voucher);
//    ArrayList<Voucher> getList();
    ConcurrentHashMap<UUID,Voucher> getList();


}
