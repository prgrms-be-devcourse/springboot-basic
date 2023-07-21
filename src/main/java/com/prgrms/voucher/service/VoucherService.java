package com.prgrms.voucher.service;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherCreator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.DiscountCreator;
import com.prgrms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherConverter voucherConverter;
    private final VoucherCreator voucherCreator;
    private final DiscountCreator discountCreator;

    public VoucherService(VoucherRepository voucherRepository, VoucherConverter voucherConverter,
            VoucherCreator voucherFactory,
            DiscountCreator discountCreator) {
        this.voucherRepository = voucherRepository;
        this.voucherConverter = voucherConverter;
        this.voucherCreator = voucherFactory;
        this.discountCreator = discountCreator;
    }

    public VoucherResponse createVoucher(int id, VoucherType voucherType, double discountAmount) {
        Discount discount = discountCreator.createDiscount(voucherType, discountAmount);
        Voucher voucher = voucherCreator.createVoucher(id, voucherType, discount);
        voucherRepository.insert(voucher);

        return new VoucherResponse(voucher);
    }

    public List<VoucherResponse> getAllVoucherList() {
        Vouchers vouchers = voucherRepository.getAllVoucher();

        return voucherConverter.convertVoucherResponses(vouchers);
    }

}

