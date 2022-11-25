package org.prgrms.kdtspringdemo.domain.voucher.model;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    long getValue();

    LocalDateTime getCreatedAt();

    CsvDto makeCsvDtoFromVoucher();
}
