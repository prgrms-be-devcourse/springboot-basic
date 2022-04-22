package org.prgrms.voucherapp.engine.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CustomerVoucherDto {
    private UUID customerId;
    private UUID voucherId;
    private String name;
    private String status;
    private String voucherType;
    private long discountAmount;

    @Override
    public String toString() {
        return String.format("customer_id : %36s, customer_name : %-10s, customer_status : %-7s, | voucher_id : %36s, type : %7s, amount : %7s",
                customerId, name, status, voucherId, voucherType, discountAmount);
    }
}
