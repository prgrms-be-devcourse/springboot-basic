package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("voucherDeserializer")
public class VoucherDeserializer implements CsvDeserializer {

  private final VoucherFactory voucherFactory;

  public VoucherDeserializer(VoucherFactory voucherFactory) {
    this.voucherFactory = voucherFactory;
  }

  @Override
  public Voucher deserialize(String csvLine) {
    StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
    VoucherType type = VoucherType.valueOf(stringTokenizer.nextToken().trim());
    UUID id = UUID.fromString(stringTokenizer.nextToken().trim());
    long value = Long.parseLong(stringTokenizer.nextToken().trim());
    LocalDateTime createdAt = LocalDateTime.parse(stringTokenizer.nextToken());

    return voucherFactory.createVoucher(id, value, type);
  }
}
