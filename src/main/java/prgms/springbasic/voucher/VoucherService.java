package prgms.springbasic.voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    Voucher createVoucher(VoucherType voucherType, UUID voucherId, String value);
    List<Voucher> getVoucherList();
}
