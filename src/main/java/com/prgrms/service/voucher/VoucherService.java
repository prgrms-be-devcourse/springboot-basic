package com.prgrms.service.voucher;

import com.prgrms.model.dto.mapper.DtoConverter;
import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.repository.voucher.VoucherRepository;
import com.prgrms.model.voucher.VoucherCreator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherCreator voucherCreator;
    private final DtoConverter dtoConverter;

    public VoucherService(VoucherRepository voucherRepository, VoucherCreator voucherCreator, DtoConverter dtoConverter) {
        this.voucherRepository = voucherRepository;
        this.voucherCreator = voucherCreator;
        this.dtoConverter = dtoConverter;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        Discount discount = voucherRequest.getDiscount();
        VoucherType voucherType = voucherRequest.getVoucherType();
        Voucher voucher = voucherCreator.createVoucher(discount, voucherType);

        return voucherRepository.insert(voucher);
    }

    public List<VoucherResponse> getAllVoucherList() {
        VoucherRegistry vouchers = voucherRepository.getAllVoucher();
        return dtoConverter.convertVoucherResponse(vouchers);
    }

}


