package com.programmers.vouchermanagement.voucher.service;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.programmers.vouchermanagement.constant.Message.NOT_FOUND_VOUCHER;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = new Voucher(UUID.randomUUID(), createVoucherRequest.discountValue(), createVoucherRequest.voucherType());
        voucherRepository.save(voucher);
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> readAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            return Collections.emptyList();
        }

        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    public VoucherResponse readById(UUID voucherId) {
        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_VOUCHER));
        return VoucherResponse.from(voucher);
    }

    public void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public void update(Voucher voucher) {
        voucherRepository.update(voucher);
    }

}
