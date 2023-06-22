package org.programers.vouchermanagement.voucher.application;

import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherRepository;
import org.programers.vouchermanagement.voucher.dto.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.VouchersResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public VouchersResponse findAll() {
        List<Voucher> result = voucherRepository.findAll();
        return new VouchersResponse(result.stream().map(VoucherResponse::new).collect(Collectors.toList()));
    }
}
