package com.voucher.vouchermanagement.service;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FindVoucherService {

  private final VoucherRepository voucherRepository;
  private static final Logger logger = LoggerFactory.getLogger(FindVoucherService.class);

  public FindVoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public List<Voucher> findAll() throws IOException {
    logger.info("findAll()");

    List<Voucher> foundVouchers = voucherRepository.findAll();

    logger.info("Vouchers = {}", foundVouchers);

    return foundVouchers;
  }
}
