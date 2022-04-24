package org.programmer.kdtspringboot.voucher;

public class CreateVoucherRequest {
    private final String type;
    private final Long value;

    public CreateVoucherRequest(String type, Long value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Long getValue() {
        return value;
    }
}
