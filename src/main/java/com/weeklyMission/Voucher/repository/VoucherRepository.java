package com.weeklyMission.Voucher.repository;

import com.weeklyMission.Voucher.domain.Voucher;
import java.util.List;

public interface VoucherRepository {

    Voucher createVoucher(Voucher voucher);

    List<Voucher> getVoucherList();

}
