package com.prgrms.voucher.service;

import com.prgrms.common.util.Generator;
import com.prgrms.voucher.model.voucher.Voucher;
import com.prgrms.voucher.model.VoucherCreator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.DiscountCreator;
import com.prgrms.voucher.repository.VoucherRepository;
import com.prgrms.voucher.service.dto.VoucherServiceCreateRequest;
import com.prgrms.voucher.service.dto.VoucherServiceListRequest;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import com.prgrms.voucher.service.mapper.VoucherConverter;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherConverter voucherConverter;
    private final VoucherCreator voucherCreator;
    private final DiscountCreator discountCreator;
    private final Generator generator;

    public VoucherService(VoucherRepository voucherRepository, VoucherConverter voucherConverter,
            VoucherCreator voucherFactory,
            DiscountCreator discountCreator, Generator generator) {
        this.voucherRepository = voucherRepository;
        this.voucherConverter = voucherConverter;
        this.voucherCreator = voucherFactory;
        this.discountCreator = discountCreator;
        this.generator = generator;
    }

    public VoucherServiceResponse createVoucher( VoucherServiceCreateRequest voucherServiceCreateRequest) {

        VoucherType voucherType = voucherServiceCreateRequest.getVoucherType();
        double discountAmount = voucherServiceCreateRequest.getDiscountAmount();

        Discount discount = discountCreator.createDiscount(voucherType, discountAmount);
        Voucher voucher = voucherCreator.createVoucher(generator, voucherType, discount);
        voucherRepository.insert(voucher);

        return new VoucherServiceResponse(voucher);
    }

    public List<VoucherServiceResponse> getAllVoucherList(
            VoucherServiceListRequest voucherServiceLIstRequest) {

        VoucherType voucherType = voucherServiceLIstRequest.voucherType();
        LocalDateTime createdAt = voucherServiceLIstRequest.startCreatedAt();

        Vouchers vouchers = voucherRepository.getAllVoucher(voucherType, createdAt);

        return voucherConverter.convertVoucherResponses(vouchers);
    }

    public VoucherServiceResponse detailVoucher(String voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException("Voucher not found with ID: " + voucherId));

        return new VoucherServiceResponse(voucher);
    }

    public String deleteByVoucherId(String voucherId) {

        return voucherRepository.deleteById(voucherId);
    }

}

