package org.prgrms.kdt.Repository;

import org.prgrms.kdt.VO.Voucher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    //voucher가 없을수도 있음
    //voucher가 어떤 dbms에서 가져오는지 모름, 몰라도 됨 -> interface
    Optional<Voucher> findById(UUID voucherId);
    ArrayList<Voucher> getVoucherList();



    void createFixedAmountVoucher(long amount, UUID voucherId);
    void createPercentDiscountVoucher(long percent, UUID voucherId);

    int numVouchers();
}
