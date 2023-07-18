package com.wonu606.vouchermanager.controller.voucher.response;

import java.util.List;

public class VoucherGetOwnedCustomersResponse {

    private final List<String> ownedCustomers;

    public VoucherGetOwnedCustomersResponse(List<String> ownedCustomers) {
        this.ownedCustomers = ownedCustomers;
    }

    public List<String> getOwnedCustomers() {
        return ownedCustomers;
    }
}
