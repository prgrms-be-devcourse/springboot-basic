package com.kdt.commandLineApp.service.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherDTO {
    private String id;
    private String type;
    private int amount;
}
