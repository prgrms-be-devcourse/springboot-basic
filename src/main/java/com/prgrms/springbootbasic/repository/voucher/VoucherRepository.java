package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Map<UUID, Voucher> getAllVouchersList();
}
