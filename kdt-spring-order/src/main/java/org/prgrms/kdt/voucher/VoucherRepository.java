package org.prgrms.kdt.voucher;

import java.util.*;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);
    Map<UUID, Voucher> getVoucherAll(); // 저장된 voucher list를 불러 오기 위해서 추˜
    Voucher insert(Voucher voucher); // voucher를 만들고 저장하기 위해서 추가

    HashMap<UUID, Voucher> getVoucherListByCustomerEmail(String inputCustomerEmail);

    void updateAssignVoucher(Voucher voucher);

    void deleteVoucher(UUID voucherId);
}
