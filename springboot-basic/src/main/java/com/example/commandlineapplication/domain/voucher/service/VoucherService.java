package com.example.commandlineapplication.domain.voucher.service;

import com.example.commandlineapplication.domain.voucher.dto.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import com.example.commandlineapplication.domain.voucher.repository.MemoryVoucherRepository;
import com.example.commandlineapplication.global.io.Input;
import com.example.commandlineapplication.global.io.Output;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

  private final static Logger LOG = LoggerFactory.getLogger(VoucherService.class);
  private final MemoryVoucherRepository memoryVoucherRepository;
  private final Output output;
  private final Input input;
  private final VoucherFactory voucherFactory;

  public void createVoucher() {
    output.printCreateOption();
    VoucherType inputVoucherType = VoucherType.of(input.selectOption());

    output.printDiscount();
    Integer discount = input.getDiscount();

    VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(inputVoucherType,
        discount);

    Voucher voucher = voucherFactory.create(voucherCreateRequest);

    save(voucher);
  }

  public Voucher save(Voucher voucher) {
    LOG.info("Voucher가 저장되었습니다.");
    return memoryVoucherRepository.insert(voucher);
  }

  public void history() {
    output.printHistory();
  }
}
