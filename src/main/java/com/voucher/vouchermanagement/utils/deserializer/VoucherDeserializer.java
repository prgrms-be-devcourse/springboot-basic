package com.voucher.vouchermanagement.utils.deserializer;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;

public class VoucherDeserializer implements CsvDeserializer<Voucher> {
    @Override
    public Voucher deserialize(String csvLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
        VoucherType voucherType = VoucherType.getVoucherTypeByName(stringTokenizer.nextToken().trim());
        UUID id = UUID.fromString(stringTokenizer.nextToken().trim());
        long value = Long.parseLong(stringTokenizer.nextToken().trim());
        LocalDateTime createdAt = LocalDateTime.parse(stringTokenizer.nextToken());

        return voucherType.create(id, value, createdAt);
    }
}
