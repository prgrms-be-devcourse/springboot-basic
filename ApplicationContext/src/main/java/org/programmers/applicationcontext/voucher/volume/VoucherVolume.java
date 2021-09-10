package org.programmers.applicationcontext.voucher.volume;

import org.programmers.applicationcontext.voucher.Voucher;
import org.programmers.applicationcontext.voucher.VoucherService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VoucherVolume {
    boolean change(List<Voucher> voucherList, BufferedReader br, UUID voucherId, VoucherService voucherService) throws IOException;
}
