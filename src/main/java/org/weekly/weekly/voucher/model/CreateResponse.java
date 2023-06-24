package org.weekly.weekly.voucher.model;

import org.weekly.weekly.voucher.domain.Voucher;

public class CreateResponse implements Response{
    private final String result;

    public CreateResponse(Voucher voucher) {
        this.result = voucher.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
