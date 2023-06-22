package org.programers.vouchermanagement.application;

import org.programers.vouchermanagement.domain.Voucher;
import org.programers.vouchermanagement.domain.VoucherRepository;
import org.programers.vouchermanagement.dto.VoucherCreationRequest;
import org.programers.vouchermanagement.dto.VoucherResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse save(VoucherCreationRequest request) {
        Voucher voucher = voucherRepository.save(new Voucher(request.getPolicy()));
        return new VoucherResponse(voucher);
    }

    public VoucherResponse findById(UUID id) {
        Voucher voucher = voucherRepository.getById(id);
        return new VoucherResponse(voucher);
    }
}
