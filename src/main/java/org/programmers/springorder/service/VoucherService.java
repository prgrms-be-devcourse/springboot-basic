package org.programmers.springorder.service;

import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;


    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<VoucherResponseDto> getAllVoucher() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponseDto::of)
                .toList();
    }

    public void save(VoucherRequestDto voucherDto) {
        Voucher voucher = Voucher.of(UUID.randomUUID(), voucherDto);
        voucherRepository.save(voucher);
        log.info("등록된 Voucher => iD: {}, type: {}, value: {}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue());
    }
}
