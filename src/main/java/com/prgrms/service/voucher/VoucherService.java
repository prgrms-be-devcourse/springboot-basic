package com.prgrms.service.voucher;

import com.prgrms.model.dto.mapper.DtoConverter;
import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final DtoConverter dtoConverter;

    public VoucherService(VoucherRepository voucherRepository, DtoConverter dtoConverter) {
        this.voucherRepository = voucherRepository;
        this.dtoConverter = dtoConverter;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        Discount discount = voucherRequest.getDiscount();
        VoucherType voucherType = voucherRequest.getVoucherType();
        Voucher voucher = voucherType.createVoucher(discount);
        
        return voucherRepository.insert(voucher);
    }

    public List<VoucherResponse> getAllVoucherList() {
        VoucherRegistry vouchers = voucherRepository.getAllVoucher();
        return dtoConverter.convertVoucherResponse(vouchers);
    }
}
