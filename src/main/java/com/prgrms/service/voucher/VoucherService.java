package com.prgrms.service.voucher;

import com.prgrms.dto.voucher.VoucherConverter;
import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.model.KeyGenerator;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherCreator;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.DiscountCreator;
import com.prgrms.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherConverter voucherConverter;
    private final KeyGenerator keyGenerator;
    private final VoucherCreator voucherCreator;
    private final DiscountCreator discountCreator;


    public VoucherService(VoucherRepository voucherRepository, VoucherConverter voucherConverter, KeyGenerator keyGenerator, VoucherCreator voucherFactory, DiscountCreator discountCreator) {
        this.voucherRepository = voucherRepository;
        this.voucherConverter = voucherConverter;
        this.keyGenerator = keyGenerator;
        this.voucherCreator = voucherFactory;
        this.discountCreator = discountCreator;
    }

    public Voucher createVoucher(VoucherType voucherType, double discountAmount) {
        int id = keyGenerator.make();
        Discount discount = discountCreator.createDiscount(voucherType,discountAmount);
        Voucher voucher = voucherCreator.createVoucher(id, voucherType,discount);

        return voucherRepository.insert(voucher);
    }

    public List<VoucherResponse> getAllVoucherList() {
        Vouchers vouchers = voucherRepository.getAllVoucher();

        return voucherConverter.convertVoucherResponse(vouchers);
    }
}
