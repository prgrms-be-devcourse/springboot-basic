package org.prgrms.spring_week1.repositories;

import java.util.List;
import org.prgrms.spring_week1.models.Voucher;

public interface VoucherRepository {

    void insert(Voucher voucher);

    //ConcurrentHashMap<UUID,Voucher> getAllVoucher();
    List<String> getAllVoucher();

}
