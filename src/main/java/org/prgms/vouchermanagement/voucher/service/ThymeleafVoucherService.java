package org.prgms.vouchermanagement.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.dto.VoucherDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThymeleafVoucherService {

    private final VoucherRepository voucherRepository;

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public void createNewVoucher(VoucherDto voucherDto) {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), voucherDto.getDiscount(), voucherDto.getVoucherType());
        voucherRepository.save(newVoucher);
    }

}
