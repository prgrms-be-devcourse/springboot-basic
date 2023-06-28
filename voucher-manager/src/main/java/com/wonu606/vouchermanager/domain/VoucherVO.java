package com.wonu606.vouchermanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherVO {

    private final String type;
    private final double value;
}
