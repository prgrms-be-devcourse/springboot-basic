package com.programmers.vouchermanagement.voucher.service;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequestDTO;
import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDTO;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherService {
    //messages
    private static final String VOUCHER_NOT_FOUND_MESSAGE =
            "There is no voucher registered.";
    //---

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponseDTO create(CreateVoucherRequestDTO createVoucherRequestDTO) {
        Voucher voucher = switch (createVoucherRequestDTO.voucherType()) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), createVoucherRequestDTO.discountValue());
            case PERCENT -> new PercentVoucher(UUID.randomUUID(), createVoucherRequestDTO.discountValue());
        };
        voucherRepository.save(voucher);
        return voucher.toResponseDTO();
    }

    public List<VoucherResponseDTO> readAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            throw new NoSuchElementException(VOUCHER_NOT_FOUND_MESSAGE);
        }
        return vouchers.stream()
                .map(Voucher::toResponseDTO)
                .toList();
    }
}
