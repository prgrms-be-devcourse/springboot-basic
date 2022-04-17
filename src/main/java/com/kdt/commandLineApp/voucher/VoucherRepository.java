package com.kdt.commandLineApp.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public void add(Voucher voucher);

    public Optional<Voucher> get(UUID id);

    public List<Voucher> getAll();

    public void remove(UUID vid);
}
