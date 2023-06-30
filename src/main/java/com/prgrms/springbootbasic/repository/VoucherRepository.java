package com.prgrms.springbootbasic.repository;

import com.prgrms.springbootbasic.domain.Voucher;
import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Map<UUID,Voucher> getAllVouchersList();
}
