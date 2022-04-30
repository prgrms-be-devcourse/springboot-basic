package org.prgrms.deukyun.voucherapp.domain.customer.domain;

import lombok.Getter;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 고객
 */
@Getter
public class Customer {

    /**
     * 아이디
     */
    private final UUID id;

    /**
     * 이름
     */
    private final String name;

    /**
     * 차단 여부
     */
    private final boolean blocked;

    /**
     * 보유 바우처 목록
     */
    private final List<Voucher> vouchers;

    public Customer(String name, boolean blocked, List<Voucher> vouchers) {
        this(UUID.randomUUID(), name, blocked, vouchers);
    }

    public Customer(UUID id, String name, boolean blocked, List<Voucher> vouchers) {
        checkArgument(id != null, "id must be provided.");
        checkArgument(name != null, "name must be provided.");
        checkArgument(vouchers != null, "vouchers must be provided");

        this.id = id;
        this.name = name;
        this.blocked = blocked;
        this.vouchers = vouchers;
    }
}
