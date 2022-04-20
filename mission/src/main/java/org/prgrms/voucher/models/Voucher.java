package org.prgrms.voucher.models;

public abstract class Voucher {

    private final Long voucherId;

    public Voucher(Long voucherId) {

        this.voucherId = voucherId;
    }

    public Long getVoucherId() {

        return voucherId;
    }
}
