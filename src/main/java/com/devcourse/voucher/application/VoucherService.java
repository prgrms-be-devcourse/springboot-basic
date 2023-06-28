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
    private final VoucherValidator voucherValidator;
    private final VoucherMapper voucherMapper;

    public VoucherService(VoucherRepository voucherRepository, VoucherValidator voucherValidator, VoucherMapper voucherMapper) {
        this.voucherRepository = voucherRepository;
        this.voucherValidator = voucherValidator;
        this.voucherMapper = voucherMapper;
    }

    public void create(CreateVoucherRequest request) {
        voucherValidator.validateRequest(request);
        Voucher voucher = voucherMapper.mapFrom(request);
        voucherRepository.save(voucher);
    }

    public List<GetVoucherResponse> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return voucherMapper.toResponseList(vouchers);
    }
}
