package org.weekly.weekly.voucher.service;

import org.springframework.stereotype.Service;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;
import org.weekly.weekly.voucher.dto.response.VouchersResponse;
import org.weekly.weekly.voucher.repository.VoucherRepository;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherCreationResponse insertVoucher(VoucherCreationRequest voucherCreationRequest) {
        Voucher voucher = voucherCreationRequest.toVoucher();
        Voucher savedVoucher = voucherRepository.insert(voucher);
        return new VoucherCreationResponse(savedVoucher);
    }

    public VouchersResponse getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return new VouchersResponse(vouchers);
    }
}
