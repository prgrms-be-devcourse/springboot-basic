package org.prgms.voucherProgram.service;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDto create(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.toEntity();
        return VoucherDto.from(voucherRepository.save(voucher));
    }

    public List<VoucherDto> findAllVoucher() {
        return voucherRepository.findAll().stream()
            .map(VoucherDto::from)
            .collect(toList());
    }
}
