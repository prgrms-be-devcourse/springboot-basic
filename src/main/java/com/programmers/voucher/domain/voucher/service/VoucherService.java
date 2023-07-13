package com.programmers.voucher.domain.voucher.service;

import com.programmers.voucher.domain.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import com.programmers.voucher.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.constant.ErrorMessage.NOT_FOUND_VOUCHER;

@Service
@Transactional(readOnly = true)
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse createVoucher(VoucherCreateRequest request) {
        Voucher voucher = voucherRepository.insert(request.toEntity());
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> getAllVouchers() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponse::from)
                .toList();
    }

    public VoucherResponse getVoucher(UUID voucherId) {
        Voucher voucher = validateExist(voucherRepository.findById(voucherId));
        return VoucherResponse.from(voucher);
    }

    @Transactional
    public VoucherResponse updateVoucher(UUID voucherId, VoucherUpdateRequest request) {
        Voucher voucher = validateExist(voucherRepository.findById(voucherId));
        voucher.update(request.voucherType(), request.discountAmount());

        return VoucherResponse.from(voucherRepository.update(voucher));
    }

    @Transactional
    public void deleteVoucher(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    private Voucher validateExist(Optional<Voucher> voucher) {
        return voucher.orElseThrow(() -> new NotFoundException(NOT_FOUND_VOUCHER));
    }
}
