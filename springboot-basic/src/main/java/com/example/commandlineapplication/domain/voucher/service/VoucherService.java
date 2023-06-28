package com.example.commandlineapplication.domain.voucher.service;

import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.repository.MemoryVoucherRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

  private final static Logger LOG = LoggerFactory.getLogger(VoucherService.class);

  private final MemoryVoucherRepository memoryVoucherRepository;

  public Voucher insert(Voucher voucher) {
    LOG.info("Voucher가 저장되었습니다.");
    return memoryVoucherRepository.insert(voucher);
  }

  public void history() {
    List<Voucher> historyList = memoryVoucherRepository.findAll();
    for (Voucher voucher : historyList) {
      System.out.println(voucher.getVoucherType() + " " + voucher.getVoucherId().toString());
    }
  }
}
