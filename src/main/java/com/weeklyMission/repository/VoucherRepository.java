package com.weeklyMission.repository;

import com.weeklyMission.domain.Voucher;
import java.util.List;

public interface VoucherRepository {

    Voucher createVoucher(Voucher voucher);

    List<Voucher> getVoucherList();

}
