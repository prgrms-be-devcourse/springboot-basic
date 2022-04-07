package com.voucher.vouchermanagement.service;

import com.voucher.vouchermanagement.model.voucher.FixedAmountVoucher;
import com.voucher.vouchermanagement.model.voucher.PercentDiscountVoucher;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddVoucherService {

  private final VoucherRepository voucherRepository;

  private static final Logger logger = LoggerFactory.getLogger(AddVoucherService.class);

  public AddVoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public void addFixedAmountVoucher(Long amount) throws IOException {
    logger.info("insertPercentDiscountVoucher()");

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amount, LocalDateTime.now());
    voucherRepository.save(voucher);

    logger.info("inserted Voucher = {}", voucher);
  }

  public void addPercentDiscountVoucher(Long amount) throws IOException {
    logger.info("insertPercentDiscountVoucher()");

    Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount, LocalDateTime.now());
    voucherRepository.save(voucher);

    logger.info("inserted Voucher = {}", voucher);
  }
}
