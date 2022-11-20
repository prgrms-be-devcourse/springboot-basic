package prgms.vouchermanagementapp.voucher.warehouse;

import prgms.vouchermanagementapp.voucher.model.Voucher;
import prgms.vouchermanagementapp.voucher.warehouse.model.VoucherRecord;

public interface VoucherWarehouse {

    void store(Voucher voucher);

    VoucherRecord getVoucherRecord();
}
