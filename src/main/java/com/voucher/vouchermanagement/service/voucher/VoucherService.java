package com.voucher.vouchermanagement.service.voucher;

import com.voucher.vouchermanagement.dto.voucher.VoucherDto;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    //@Transactional
    public CreateVoucherResponse createVoucher(VoucherType voucherType, Long value) {
        //this.voucherRepository.insert(voucherType.create(UUID.randomUUID(), value, LocalDateTime.now()));
        return null;
    }

    public VoucherDto findById(UUID id) {
        return null;
    }

    //@Transactional(readOnly = true)
    public List<VoucherDto> findAll() {
        //return this.voucherRepository.findAll();
        return Collections.emptyList();
    }

}
