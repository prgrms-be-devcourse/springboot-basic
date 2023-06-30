package com.programmers.springbasic.domain.voucher.service;

import com.programmers.springbasic.domain.voucher.dto.request.CreateFixedAmountVoucherRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.request.CreatePercentDiscountVoucherRequestDTO;
import com.programmers.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherOption;
import com.programmers.springbasic.domain.voucher.repository.VoucherFileRepository;
import com.programmers.springbasic.domain.voucher.view.VoucherInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherFileRepository voucherFileRepository;

    public void createFixedAmountVoucher(CreateFixedAmountVoucherRequestDTO createFixedAmountVoucherRequestDTO) {
        double fixedAmount = getFixedAmountFromInput(createFixedAmountVoucherRequestDTO);
        FixedAmountVoucher voucher = new FixedAmountVoucher(fixedAmount);

        voucherFileRepository.save(voucher);
    }

    public void createPercentDiscountVoucher(CreatePercentDiscountVoucherRequestDTO createPercentDiscountVoucherRequestDTO) {
        double percentDiscount = getPercentDiscountFromInput(createPercentDiscountVoucherRequestDTO);
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(percentDiscount);

        voucherFileRepository.save(voucher);
    }

    public List<String> getAllFixedAmountVoucherInfo() {
        List<Voucher> fixedAmountVouchers = voucherFileRepository.findAllByVoucherType(VoucherOption.FIXED_AMOUNT_VOUCHER);

        return fixedAmountVouchers.stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    public List<String> getAllPercentDiscountVoucherInfo() {
        List<Voucher> percentDiscountVouchers = voucherFileRepository.findAllByVoucherType(VoucherOption.PERCENT_DISCOUNT_VOUCHER);

        return percentDiscountVouchers.stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    private double getFixedAmountFromInput(CreateFixedAmountVoucherRequestDTO createFixedAmountVoucherRequestDTO) {
        return Double.parseDouble(createFixedAmountVoucherRequestDTO.getInputFixedAmount());
    }

    private double getPercentDiscountFromInput(CreatePercentDiscountVoucherRequestDTO createPercentDiscountVoucherRequestDTO) {
        return Double.parseDouble(createPercentDiscountVoucherRequestDTO.getInputPercent());
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
                .append(VoucherInfo.VOUCHER_VALID_INFO_MESSAGE.getInfoMessage() + voucher.isActive() + "\n");

        return voucherInfo.toString();
    }
}
