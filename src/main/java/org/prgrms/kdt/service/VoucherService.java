package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.dto.CreateVoucherDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public boolean createVoucher(CreateVoucherDto createVoucherDto) {
        Voucher newVoucher = new Voucher(createVoucherDto.voucherType(), createVoucherDto.discountAmount());
        Optional<Voucher> returnedVoucher = voucherRepository.saveVoucher(newVoucher);
        return returnedVoucher.isPresent();
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllVouchers();
    }
}
