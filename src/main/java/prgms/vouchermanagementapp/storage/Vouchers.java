package prgms.vouchermanagementapp.storage;

import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.storage.model.VoucherRecord;

public interface Vouchers {

    void store(Voucher voucher);

    VoucherRecord getVoucherRecord();
}
