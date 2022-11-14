package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.dto.CreateVoucherDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public boolean createVoucher(CreateVoucherDto createVoucherDto) {
        Voucher newVoucher = new Voucher(createVoucherDto.voucherType(), createVoucherDto.discountAmount());
        return voucherRepository.save(newVoucher);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAll();
    }
}
