package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.service.CreateVoucherDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;

@Component("voucherDeserializer")
public class VoucherDeserializer implements CsvDeserializer {

    @Override
    public Voucher deserialize(String csvLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
        VoucherType voucherType = VoucherType.getVoucherTypeByName(stringTokenizer.nextToken().trim());
        UUID id = UUID.fromString(stringTokenizer.nextToken().trim());
        long value = Long.parseLong(stringTokenizer.nextToken().trim());
        LocalDateTime createdAt = LocalDateTime.parse(stringTokenizer.nextToken());

        return voucherType.create(new CreateVoucherDto(id, value, createdAt));
    }
}
