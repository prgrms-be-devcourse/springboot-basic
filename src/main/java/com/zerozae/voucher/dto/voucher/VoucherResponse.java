package com.zerozae.voucher.dto.voucher;

import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class VoucherResponse {

    private String voucherId;
    private long discount;
    private VoucherType voucherType;
    private UseStatusType useStatusType;

    public VoucherResponse(String voucherId, long discount, VoucherType voucherType, UseStatusType useStatusType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
        this.useStatusType = useStatusType;
    }

    public static VoucherResponse toDto(Voucher voucher){
        return new VoucherResponse(
                voucher.getVoucherId().toString(),
                voucher.getDiscount(),
                voucher.getVoucherType(),
                voucher.getUseStatusType());
    }

    public String getInfo(){
        return """
                바우처 번호  : %s
                바우처 종류  : %s
                할인 정보   : %s
                사용 여부   : %s
                
                --------------------------------------
                """.formatted(voucherId, voucherType, discount, useStatusType.getDescription());
    }
}

