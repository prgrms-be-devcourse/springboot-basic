package prgms.vouchermanagementapp.storage;

import prgms.vouchermanagementapp.storage.model.VoucherRecord;
import prgms.vouchermanagementapp.voucher.model.Voucher;

public interface Vouchers {

    void store(Voucher voucher);

    VoucherRecord getVoucherRecord();
}
