package com.zerozae.voucher.service.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.repository.voucher.VoucherRepository;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(VoucherRequest voucherRequest){
        VoucherType voucherType = voucherRequest.getVoucherType();
        try {
            Voucher voucher = switch (voucherType) {
                case FIXED -> new FixedDiscountVoucher(voucherRequest.getDiscount());
                case PERCENT ->  new PercentDiscountVoucher(voucherRequest.getDiscount());
            };
            voucherRepository.save(voucher);
            return VoucherResponse.toDto(voucher);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public List<VoucherResponse> findAllVouchers(){
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponse::toDto)
                .toList();
    }
}
