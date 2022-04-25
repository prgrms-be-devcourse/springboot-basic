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

    private final UUID id;
    private final String name;
    private final boolean blackList;
    private final List<Voucher> vouchers;

    public Customer(String name, boolean blackList, List<Voucher> vouchers) {
        this(UUID.randomUUID(), name, blackList, vouchers);
    }

    public Customer(UUID id, String name, boolean blackList, List<Voucher> vouchers) {
        checkArgument(id != null, "id must be provided.");
        checkArgument(name != null, "name must be provided.");
        checkArgument(vouchers != null, "vouchers must be provided");

        this.id = id;
        this.name = name;
        this.blackList = blackList;
        this.vouchers = vouchers;
    }
}
