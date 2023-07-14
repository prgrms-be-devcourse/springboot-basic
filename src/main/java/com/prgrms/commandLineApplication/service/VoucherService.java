package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.repository.voucher.VoucherRepository;
import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.VoucherFactory;
import com.prgrms.commandLineApplication.voucher.dto.VoucherCreateDto;
import com.prgrms.commandLineApplication.voucher.dto.VoucherMapper;
import com.prgrms.commandLineApplication.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public List<VoucherResponseDto> findAllVouchers() {
    return voucherRepository.findAll()
            .stream()
            .map(VoucherMapper::mapToReposnse)
            .toList();
  }

  public VoucherResponseDto findById(UUID id) {
    Voucher voucher = voucherRepository.findById(id);
    return VoucherMapper.mapToReposnse(voucher);
  }

  public UUID create(VoucherCreateDto voucherCreateDto) {
    Voucher createdVoucher = VoucherFactory.of(UUID.randomUUID(), voucherCreateDto.discountType(), voucherCreateDto.discountAmount());
    voucherRepository.save(createdVoucher);
    return createdVoucher.getVoucherId();
  }

}
