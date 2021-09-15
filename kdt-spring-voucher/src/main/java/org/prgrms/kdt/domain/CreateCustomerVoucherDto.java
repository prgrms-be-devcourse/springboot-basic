package org.prgrms.kdt.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.kdt.enumType.VoucherStatus;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
public class CreateCustomerVoucherDto {

    private UUID customerVoucherId;
    private UUID customerId;
    private UUID voucherId;
    private String name;
    private Enum<VoucherStatus> voucherType;
    private Long discount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime allocatedAt;

    public CreateCustomerVoucherDto(UUID customerVoucherId, UUID customerId, UUID voucherId, String name, Enum<VoucherStatus> voucherType, Long discount, LocalDateTime allocatedAt) {
        this.customerVoucherId = customerVoucherId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.name = name;
        this.voucherType = voucherType;
        this.discount = discount;
        this.allocatedAt = allocatedAt;
    }
}
