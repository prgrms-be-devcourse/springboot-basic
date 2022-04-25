package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private VoucherRepository voucherRepository;

    public VoucherService(MemoryVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Response<VoucherResponseDto> create(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        Voucher savedVoucher = voucherRepository.create(voucher);
        VoucherResponseDto voucherResponseDto = VoucherMapper.domainToResponseDto(savedVoucher);

        Response<VoucherResponseDto> response = new Response<>(Response.State.SUCCESS, voucherResponseDto);
        return response;
    }
}
