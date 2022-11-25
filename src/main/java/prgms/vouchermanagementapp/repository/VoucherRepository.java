package prgms.vouchermanagementapp.repository;

import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.VoucherRecord;

public interface VoucherRepository {

    void save(Voucher voucher);

    VoucherRecord getVoucherRecord();
}
