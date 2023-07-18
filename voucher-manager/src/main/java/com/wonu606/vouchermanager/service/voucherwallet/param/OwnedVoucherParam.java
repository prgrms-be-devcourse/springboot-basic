package com.wonu606.vouchermanager.service.voucherwallet.param;

import com.wonu606.vouchermanager.domain.customer.email.Email;

public class OwnedVoucherParam {

    private final Email email;

    public OwnedVoucherParam(Email email) {
        this.email = email;
    }

    public String getEmail() {return email.getAddress();}
}
