package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.UsernameRequest;
import com.prgmrs.voucher.dto.request.VoucherIdRequest;
import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.request.VoucherSearchRequest;
import com.prgmrs.voucher.dto.response.RemoveResponse;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.enums.DiscountType;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.validator.VoucherValidator;
import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.Percent;
import com.prgmrs.voucher.model.wrapper.Username;
import com.prgmrs.voucher.repository.VoucherRepository;
import com.prgmrs.voucher.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherValidator voucherValidator;
    private final IdGenerator idGenerator;

    public VoucherService(VoucherRepository voucherRepository, VoucherValidator voucherValidator, IdGenerator idGenerator) {
        this.voucherRepository = voucherRepository;
        this.voucherValidator = voucherValidator;
        this.idGenerator = idGenerator;
    }

    public VoucherResponse createVoucher(VoucherRequest voucherRequest) {
        DiscountType discountType = DiscountType.fromString(voucherRequest.discountType());
        Long validatedLongValue =
                voucherValidator.convertToLongWithValidation(voucherRequest.discountStringValue(), discountType);

        UUID uuid = idGenerator.generate();
        Voucher voucher;

        switch (discountType) {
            case FIXED_AMOUNT_DISCOUNT -> {
                Amount amount = new Amount(validatedLongValue);
                FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
                voucher = new Voucher(uuid, fixedAmountDiscountStrategy);
            }
            case PERCENT_DISCOUNT -> {
                Percent percent = new Percent(validatedLongValue);
                PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
                voucher = new Voucher(uuid, percentDiscountStrategy);
            }
            default -> throw new NoSuchVoucherTypeException("unexpected voucher type");
        }

        voucherRepository.save(voucher);

        return new VoucherResponse(uuid, voucher.discountStrategy());
    }

    public VoucherListResponse findAll() {
        return new VoucherListResponse(voucherRepository.findAll());
    }

    public VoucherListResponse getAssignedVoucherListByUsername(UsernameRequest usernameRequest) {
        Username username = new Username(usernameRequest.username());
        return new VoucherListResponse(voucherRepository.getAssignedVoucherListByUsername(username));
    }

    public VoucherListResponse getNotAssignedVoucher() {
        return new VoucherListResponse(voucherRepository.getNotAssignedVoucherList());
    }

    public VoucherListResponse getAssignedVoucherList() {
        return new VoucherListResponse(voucherRepository.getAssignedVoucherList());
    }

    public VoucherListResponse findByCreationTimeAndDiscountType(VoucherSearchRequest voucherSearchRequest) {
        LocalDateTime startDate = voucherSearchRequest.startDate();
        LocalDateTime endDate = voucherSearchRequest.endDate();
        DiscountType discountType = DiscountType.fromString(voucherSearchRequest.discountType());
        return new VoucherListResponse(voucherRepository.findByCreationTimeAndDiscountType(startDate, endDate, discountType.getValue()));
    }

    public RemoveResponse removeVoucher(VoucherIdRequest voucherIdRequest) {
        UUID uuid = UUID.fromString(voucherIdRequest.voucherUuid());
        return new RemoveResponse(voucherRepository.removeVoucher(uuid));
    }

    public VoucherResponse findById(VoucherIdRequest voucherIdRequest) {
        UUID uuid = UUID.fromString(voucherIdRequest.voucherUuid());
        Voucher voucher = voucherRepository.findById(uuid);
        return new VoucherResponse(voucher.voucherId(), voucher.discountStrategy());
    }
}
