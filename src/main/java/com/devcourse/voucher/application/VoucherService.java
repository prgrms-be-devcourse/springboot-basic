package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(CreateVoucherRequest request) {
        VoucherValidator.validateRequest(request);
        Voucher voucher = VoucherMapper.toEntity(request);
        voucherRepository.save(voucher);
    }

    public List<GetVoucherResponse> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        return vouchers.stream()
                .map(VoucherMapper::toResponse)
                .toList();
    }
}
