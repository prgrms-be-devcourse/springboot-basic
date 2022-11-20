package prgms.vouchermanagementapp.repository;

import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.VoucherRecord;

public interface Vouchers {

    void store(Voucher voucher);

    VoucherRecord getVoucherRecord();
}
