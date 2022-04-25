package com.kdt.commandLineApp.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherDTO {
    private String id;
    private String type;
    private int amount;
}
