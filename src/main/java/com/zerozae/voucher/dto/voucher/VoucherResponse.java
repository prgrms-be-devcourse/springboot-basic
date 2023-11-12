package com.zerozae.voucher.dto.voucher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class VoucherResponse {

    private UUID voucherId;
    private long discount;
    private VoucherType voucherType;
    private UseStatusType useStatusType;
    private LocalDateTime createdAt;

    public VoucherResponse(UUID voucherId, long discount, VoucherType voucherType, UseStatusType useStatusType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
        this.useStatusType = useStatusType;
        this.createdAt = createdAt;
    }

    public static VoucherResponse toDto(Voucher voucher) {
        return new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getDiscount(),
                voucher.getVoucherType(),
                voucher.getUseStatusType(),
                voucher.getCreatedAt());
    }

    @JsonIgnore
    public String getInfo() {
        return """
                바우처 번호  : %s
                바우처 종류  : %s
                할인 정보   : %s
                사용 여부   : %s
                생성 일자   : %s
                
                --------------------------------------
                """.formatted(voucherId.toString(), voucherType, discount, useStatusType.getDescription(), createdAt.toString());
    }
}
