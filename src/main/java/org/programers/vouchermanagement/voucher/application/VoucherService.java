package org.programers.vouchermanagement.voucher.application;

import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherRepository;
import org.programers.vouchermanagement.voucher.domain.VoucherType;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse save(VoucherCreationRequest request) {
        VoucherType type = request.getType();
        Voucher savingVoucher = type.createVoucher(UUID.randomUUID(), request.getValue());
        Voucher voucher = voucherRepository.save(savingVoucher);
        return new VoucherResponse(voucher);
    }

    public VoucherResponse findById(UUID id) {
        Voucher voucher = voucherRepository.getById(id);
        return new VoucherResponse(voucher);
    }

    public VouchersResponse findByType(VoucherType type) {
        List<Voucher> vouchers = voucherRepository.findByType(type);
        List<VoucherResponse> responses = vouchers.stream()
                .map(VoucherResponse::new)
                .collect(Collectors.toList());
        return new VouchersResponse(responses);
    }

    public VouchersResponse findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        List<VoucherResponse> responses = vouchers.stream()
                .map(VoucherResponse::new)
                .collect(Collectors.toList());
        return new VouchersResponse(responses);
    }

    @Transactional
    public void update(VoucherUpdateRequest request) {
        Voucher voucher = new Voucher(request.getId(), request.getPolicy(), request.getType());
        voucherRepository.update(voucher);
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.deleteById(id);
    }
}
