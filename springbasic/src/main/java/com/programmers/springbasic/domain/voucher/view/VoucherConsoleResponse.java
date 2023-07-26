package com.programmers.springbasic.domain.voucher.view;

import com.programmers.springbasic.domain.voucher.dto.response.VoucherResponseDTO;

public class VoucherConsoleResponse {
    public static String getVoucherInfo(VoucherResponseDTO voucherResponseDTO) {
        StringBuilder sb = new StringBuilder();

        sb.append(VoucherInfo.VOUCHER_ID_INFO_MESSAGE.getInfoMessage() + voucherResponseDTO.getCode())
                .append(VoucherInfo.VOUCHER_VALUE_INFO_MESSAGE.getInfoMessage() + voucherResponseDTO.getValue())
                .append(VoucherInfo.VOUCHER_TYPE_INFO_MESSAGE.getInfoMessage() + voucherResponseDTO.getVoucherType())
                .append(VoucherInfo.VOUCHER_EXPIRE_INFO_MESSAGE.getInfoMessage() + voucherResponseDTO.getExpirationDate())
                .append(VoucherInfo.VOUCHER_CUSTOMER_INFO_MESSAGE.getInfoMessage() + voucherResponseDTO.getCustomerId())
                .append(VoucherInfo.VOUCHER_VALID_INFO_MESSAGE.getInfoMessage() + voucherResponseDTO.isActive());

        return sb.toString();
    }
}
