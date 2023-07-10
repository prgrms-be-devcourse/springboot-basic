package com.example.commandlineapplication.domain.voucher.service;

import com.example.commandlineapplication.domain.voucher.dto.mapper.VoucherMapper;
import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.dto.response.VoucherResponse;
import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import com.example.commandlineapplication.domain.voucher.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoucherService {

  private static final Logger LOG = LoggerFactory.getLogger(VoucherService.class);
  private final VoucherRepository voucherRepository;
  private final VoucherFactory voucherFactory;
  private final VoucherMapper voucherMapper;

  @Transactional
  public void createVoucher(VoucherType inputVoucherType, Integer inputDiscount) {
    VoucherCreateRequest voucherCreateRequest = voucherMapper.toCreateRequest(UUID.randomUUID(),
        inputVoucherType,
        inputDiscount);
    Voucher voucher = voucherFactory.create(voucherCreateRequest);

    save(voucher);
  }

  public Voucher save(Voucher voucher) {
    LOG.info(
        "Voucher가 저장되었습니다. ID : " + voucher.getVoucherId() + " type : " + voucher.getVoucherType()
            .name());
    return voucherRepository.insert(voucher);
  }

  @Transactional
  public void deleteVoucher(UUID voucherId) {
    Voucher foundVoucher = voucherRepository.findById(voucherId)
        .orElseThrow(IllegalArgumentException::new);

    voucherRepository.deleteById(foundVoucher.getVoucherId());
  }

  @Transactional(readOnly = true)
  public List<VoucherResponse> findVouchers() {
    return voucherRepository.findAll()
        .stream()
        .map(voucherMapper::voucherToResponse)
        .collect(Collectors.toList());
  }
}
