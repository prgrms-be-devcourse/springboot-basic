package org.prgrms.kdt.controller.dto;

import org.prgrms.kdt.view.VoucherMenu;

public class CreateVoucherRequest {

    private long value;
    private String type;

    public CreateVoucherRequest(long value, String type) {
        this.value = value;
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

}
