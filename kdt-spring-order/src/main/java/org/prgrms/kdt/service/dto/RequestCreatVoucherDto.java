package org.prgrms.kdt.service.dto;

public class RequestCreatVoucherDto {

    private int type;
    private long amount;

    public RequestCreatVoucherDto(int type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
