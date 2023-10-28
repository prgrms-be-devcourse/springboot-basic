package org.prgms.kdtspringweek1.voucher.service;

import org.prgms.kdtspringweek1.controller.dto.voucherDto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher registerVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public List<FindVoucherResponseDto> searchAllVouchers() {
        return voucherRepository.findAllVouchers().stream()
                .map(FindVoucherResponseDto::new)
                .collect(Collectors.toList());
    }

    public Optional<FindVoucherResponseDto> searchVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId).map(FindVoucherResponseDto::new);
    }

    public Voucher update(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
