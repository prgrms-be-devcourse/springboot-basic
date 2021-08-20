package com.org.prgrms.mission1;

import java.util.*;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);


    List<Voucher> findAll();

    Voucher insert(Voucher voucher);
}
