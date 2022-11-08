package org.prgrms.kdtspringdemo.voucher.model;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    VoucherType getVoucherType();
    String discountValue();
    CsvDto makeCsvDtoFromVoucher();
}
