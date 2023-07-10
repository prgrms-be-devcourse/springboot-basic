package com.programmers.springbasic.domain.voucher.service;

import com.programmers.springbasic.domain.customer.validator.CustomerIdValidator;
import com.programmers.springbasic.domain.voucher.repository.VoucherRepository;
import com.programmers.springbasic.domain.voucher.validator.FixedAmountVoucherCreateRequestValidator;
import com.programmers.springbasic.domain.voucher.validator.PercentDiscountVoucherCreateRequestValidator;
import com.programmers.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.validator.VoucherOptionValidator;
import com.programmers.springbasic.domain.voucher.view.VoucherOption;
import com.programmers.springbasic.domain.voucher.validator.VoucherCodeValidator;
import com.programmers.springbasic.domain.voucher.view.VoucherInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public void createFixedAmountVoucher(FixedAmountVoucherCreateRequestValidator fixedAmountVoucherCreateRequestValidator, VoucherCodeValidator voucherCodeValidator) {
        double fixedAmount = getFixedAmountFromInput(fixedAmountVoucherCreateRequestValidator);

        UUID customerId = UUID.fromString(voucherCodeValidator.getInputVoucherCode());//getUUID(voucherCodeValidator.getInputVoucherCode());

        FixedAmountVoucher voucher = new FixedAmountVoucher(fixedAmount, customerId);

        voucherRepository.save(voucher);
    }

    public void createPercentDiscountVoucher(PercentDiscountVoucherCreateRequestValidator percentDiscountVoucherCreateRequestValidator, VoucherCodeValidator voucherCodeValidator) {
        double percentDiscount = getPercentDiscountFromInput(percentDiscountVoucherCreateRequestValidator);

        UUID customerId = UUID.fromString(voucherCodeValidator.getInputVoucherCode());

        PercentDiscountVoucher voucher = new PercentDiscountVoucher(percentDiscount, customerId);

        voucherRepository.save(voucher);
    }

    public List<String> getAllFixedAmountVoucherInfo() {
        List<Voucher> fixedAmountVouchers = voucherRepository.findAllByVoucherType(VoucherOption.FIXED_AMOUNT_VOUCHER);

        return fixedAmountVouchers.stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    public List<String> getAllPercentDiscountVoucherInfo() {
        List<Voucher> percentDiscountVouchers = voucherRepository.findAllByVoucherType(VoucherOption.PERCENT_DISCOUNT_VOUCHER);

        return percentDiscountVouchers.stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    public List<String> getAllVoucherInfoByCustomerId(CustomerIdValidator customerIdValidator) {
        UUID customerId = UUID.fromString(customerIdValidator.getInputCustomerId());

        List<Voucher> vouchers = voucherRepository.findAllByCustomerId(customerId);

        return vouchers.stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    public List<String> getAllCustomerIdByVoucherType(VoucherOptionValidator voucherOptionValidator) {
        String voucherType = voucherOptionValidator.getVoucherOption();

        return voucherRepository.findAllCustomerIdByVoucherType(voucherType).stream()
                .map(uuid -> uuid.toString())
                .collect(Collectors.toList());
    }

    public String findVoucher(VoucherCodeValidator voucherCodeValidator) {
        UUID voucherCode = UUID.fromString(voucherCodeValidator.getInputVoucherCode());

        Voucher voucher = voucherRepository.findByCode(voucherCode).orElseThrow();

        return getInfo(voucher);
    }

    public void updateVoucher() {   // TODO: update voucher

    }

    public void removeVoucher(VoucherCodeValidator voucherCodeValidator) {
        UUID voucherCode = UUID.fromString(voucherCodeValidator.getInputVoucherCode());

        voucherRepository.delete(voucherCode);
    }

    private double getFixedAmountFromInput(FixedAmountVoucherCreateRequestValidator fixedAmountVoucherCreateRequestValidator) {
        return Double.parseDouble(fixedAmountVoucherCreateRequestValidator.getInputFixedAmount());
    }

    private double getPercentDiscountFromInput(PercentDiscountVoucherCreateRequestValidator percentDiscountVoucherCreateRequestValidator) {
        return Double.parseDouble(percentDiscountVoucherCreateRequestValidator.getInputPercent());
    }

    private String getInfo(Voucher voucher) {
        StringBuilder voucherInfo = new StringBuilder();

        switch (voucher.getVoucherType()) {
            case FIXED_AMOUNT_VOUCHER: {
                voucherInfo.append(VoucherInfo.VOUCHER_TYPE_INFO_MESSAGE.getInfoMessage() + VoucherOption.FIXED_AMOUNT_VOUCHER.getVoucherOption() + "\n")
                        .append(VoucherInfo.VOUCHER_VALUE_INFO_MESSAGE.getInfoMessage() + String.format("%.2f", voucher.getValue()) + "\n");
                break;
            }
            case PERCENT_DISCOUNT_VOUCHER: {
                voucherInfo.append(VoucherInfo.VOUCHER_TYPE_INFO_MESSAGE.getInfoMessage() + VoucherOption.PERCENT_DISCOUNT_VOUCHER.getVoucherOption() + "\n")
                        .append(VoucherInfo.VOUCHER_VALUE_INFO_MESSAGE.getInfoMessage() + String.format("%.0f%%", voucher.getValue()) + "\n");
                break;
            }
        }

        voucherInfo.append(VoucherInfo.VOUCHER_ID_INFO_MESSAGE.getInfoMessage() + voucher.getCode() + "\n")
                .append(VoucherInfo.VOUCHER_EXPIRE_INFO_MESSAGE.getInfoMessage() + voucher.getExpirationDate() + "\n")
                .append(VoucherInfo.VOUCHER_VALID_INFO_MESSAGE.getInfoMessage() + voucher.isActive() + "\n")
                .append(VoucherInfo.VOUCHER_CUSTOMER_INFO_MESSAGE.getInfoMessage() + voucher.getCustomerId() + "\n");

        return voucherInfo.toString();
    }
}
