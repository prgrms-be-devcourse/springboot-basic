package org.prgrms.deukyun.voucherapp.domain.common.repository;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@Component
public class IdSupplier implements Supplier<UUID> {

    @Override
    public UUID get() {
        return UUID.randomUUID();
    }
}
