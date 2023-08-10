package org.prgrms.kdt.voucher.service.dto;

import org.prgrms.kdt.voucher.domain.VoucherType;

public class SearchRequest {
    private final long page;
    private final long recordSize;
    private final long offset;
    private final VoucherType voucherType;

    public SearchRequest(long page, long recordSize, VoucherType voucherType) {
        this.page = page;
        this.recordSize = recordSize;
        this.offset = (page - 1) * recordSize;
        this.voucherType = voucherType;
    }

    public long getPage() {
        return page;
    }

    public long getRecordSize() {
        return recordSize;
    }

    public long getOffset() {
        return offset;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}