package com.prgrms.voucher.service;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherCreator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.DiscountCreator;
import com.prgrms.voucher.repository.VoucherRepository;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import com.prgrms.voucher.service.mapper.VoucherConverter;
import java.time.LocalDateTime;
import java.util.Optional;
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

    public VoucherServiceResponse createVoucher(int id, VoucherType voucherType,
            double discountAmount, LocalDateTime createdAt) {
        Discount discount = discountCreator.createDiscount(voucherType, discountAmount);
        Voucher voucher = voucherCreator.createVoucher(id, voucherType, discount, createdAt);
        voucherRepository.insert(voucher);

        return new VoucherServiceResponse(voucher);
    }

    public List<VoucherServiceResponse> getAllVoucherList(VoucherType voucherType,
            LocalDateTime createdAt) {
        Vouchers vouchers = voucherRepository.getAllVoucher(voucherType, createdAt);

        return voucherConverter.convertVoucherResponses(vouchers);
    }

    public VoucherServiceResponse detailVoucher(int voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);

        if (voucher.isPresent()) {
            VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(
                    voucher.get());
            return voucherServiceResponse;
        }
        return null;
    }

    public int deleteByVoucherId(int voucherId) {

        return voucherRepository.deleteById(voucherId);
    }

}

