package org.prgrms.kdt.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVoucherRequest {

    String voucherType;
    String discount;

    public CreateVoucherRequest(String voucherType, String discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }
}
