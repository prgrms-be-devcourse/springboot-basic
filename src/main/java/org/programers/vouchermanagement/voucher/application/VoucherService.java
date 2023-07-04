package org.programers.vouchermanagement.voucher.application;

import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherRepository;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile({"test", "default"})
@Transactional(readOnly = true)
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse save(VoucherCreationRequest request) {
        Voucher voucher = voucherRepository.save(new Voucher(request.getPolicy(), request.getType()));
        return new VoucherResponse(voucher);
    }

    public VoucherResponse findById(UUID id) {
        Voucher voucher = voucherRepository.getById(id);
        return new VoucherResponse(voucher);
    }

    public VouchersResponse findAll() {
        List<Voucher> result = voucherRepository.findAll();
        return new VouchersResponse(result.stream().map(VoucherResponse::new).collect(Collectors.toList()));
    }

    @Transactional
    public void update(VoucherUpdateRequest request) {
        voucherRepository.update(new Voucher(request.getId(), request.getPolicy(), request.getType()));
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.deleteById(id);
    }
}
