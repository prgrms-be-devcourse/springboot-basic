package com.example.commandlineapplication.domain.voucher.service;

import com.example.commandlineapplication.domain.voucher.dto.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import com.example.commandlineapplication.domain.voucher.repository.MemoryVoucherRepository;
import com.example.commandlineapplication.global.io.Console;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

  private final static Logger LOG = LoggerFactory.getLogger(VoucherService.class);
  private final MemoryVoucherRepository memoryVoucherRepository;
  private final Console console;
  private final VoucherFactory voucherFactory;

  public void createVoucher() {
    VoucherType inputVoucherType = console.selectVoucherTypeOption();

    Integer inputDiscount = console.selectDiscount();

    VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(inputVoucherType,
        inputDiscount);

    Voucher voucher = voucherFactory.create(voucherCreateRequest);

    save(voucher);
  }

  public Voucher save(Voucher voucher) {
    LOG.info("Voucher가 저장되었습니다.");
    return memoryVoucherRepository.insert(voucher);
  }

  public void history() {
    console.printHistory();
  }
}
