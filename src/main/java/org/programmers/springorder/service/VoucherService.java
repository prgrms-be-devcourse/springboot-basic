package org.programmers.springorder.service;

import org.programmers.springorder.dto.VoucherRequestDto;
import org.programmers.springorder.dto.VoucherResponseDto;
import org.programmers.springorder.model.Voucher;
import org.programmers.springorder.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);


    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<VoucherResponseDto> getAllVoucher(){
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
