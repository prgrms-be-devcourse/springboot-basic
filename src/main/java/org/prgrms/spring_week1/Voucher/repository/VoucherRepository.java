package org.prgrms.spring_week1.Voucher.repository;

import java.util.List;
import org.prgrms.spring_week1.Voucher.model.Voucher;

public interface VoucherRepository {

    void insert(Voucher voucher);

    //ConcurrentHashMap<UUID,Voucher> getAllVoucher();
    List<String> getAllVoucher();

}
