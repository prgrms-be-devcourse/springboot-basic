package com.weeklyMission.voucher.repository;

import com.weeklyMission.voucher.domain.Voucher;
import java.util.List;

public interface VoucherRepository {

    Voucher createVoucher(Voucher voucher);

    List<Voucher> getVoucherList();

}
