package com.prgrms.service.voucher;

import com.prgrms.model.voucher.dto.mapper.DtoConverter;
import com.prgrms.model.voucher.dto.VoucherRequest;
import com.prgrms.model.voucher.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.dto.discount.Discount;
import com.prgrms.repository.voucher.VoucherRepository;
import com.prgrms.util.KeyGenerator;
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
        int id = KeyGenerator.make();
        Discount discount = voucherRequest.discount();

        Voucher voucher = voucherRequest.voucherType().createVoucher(id, discount);

        return voucherRepository.insert(voucher);
    }

    public List<VoucherResponse> getAllVoucherList() {
        VoucherRegistry vouchers = voucherRepository.getAllVoucher();
        return dtoConverter.convertVoucherResponse(vouchers);
    }
}
