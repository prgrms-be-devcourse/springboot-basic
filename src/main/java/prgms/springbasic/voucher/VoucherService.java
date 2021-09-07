package prgms.springbasic.voucher;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VoucherService {
    Voucher createVoucher(VoucherType voucherType, UUID voucherId, String value) throws IOException;

    List<Voucher> getVoucherList() throws IOException;
}
