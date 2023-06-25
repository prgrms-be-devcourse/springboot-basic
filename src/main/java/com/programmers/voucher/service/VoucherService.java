package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherFactory;
import com.programmers.voucher.dto.reponse.VoucherInfoResponse;
import com.programmers.voucher.dto.request.VoucherCreationRequest;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherCreationRequest voucherCreationRequest) {
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);
        voucherRepository.save(voucher);
        return voucher.getVoucherId();
    }

    public List<VoucherInfoResponse> findVoucherList() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList.stream()
                .map(voucher -> new VoucherInfoResponse(voucher.getVoucherId(), voucher.getDiscountAmount()))
                .toList();
    }

}
