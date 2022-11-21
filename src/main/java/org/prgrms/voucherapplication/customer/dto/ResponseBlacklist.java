package org.prgrms.voucherapplication.customer.dto;

import java.math.BigInteger;

public class ResponseBlacklist {

    private final BigInteger blacklistId;
    private final String name;

    public ResponseBlacklist(BigInteger blacklistId, String name) {
        this.blacklistId = blacklistId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return blacklistId + " " + name;
    }
}
