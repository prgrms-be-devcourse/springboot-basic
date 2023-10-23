package com.programmers.vouchermanagement.voucher.service;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
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

    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = new Voucher(UUID.randomUUID(), createVoucherRequest.discountValue(), createVoucherRequest.voucherType());
        voucherRepository.save(voucher);
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> readAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            throw new NoSuchElementException(VOUCHER_NOT_FOUND_MESSAGE);
        }
        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }
}
