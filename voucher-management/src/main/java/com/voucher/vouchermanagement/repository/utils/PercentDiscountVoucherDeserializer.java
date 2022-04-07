package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.voucher.PercentDiscountVoucher;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("percentDiscountVoucherDeserializer")
public class PercentDiscountVoucherDeserializer implements CsvDeserializer<Voucher> {

  private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucherDeserializer.class);

  @Override
  public Voucher deserialize(String csvLine) {
    logger.info("deserialize()");

    StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
    VoucherType type = VoucherType.valueOf(stringTokenizer.nextToken().trim());
    UUID id = UUID.fromString(stringTokenizer.nextToken().trim());
    long value = Long.parseLong(stringTokenizer.nextToken().trim());
    LocalDateTime createdAt = LocalDateTime.parse(stringTokenizer.nextToken());

    logger.info("{} -> [{}] [{}] [{}] [{}]", csvLine, type, id, value, createdAt);

    return new PercentDiscountVoucher(id, value, createdAt);
  }
}
