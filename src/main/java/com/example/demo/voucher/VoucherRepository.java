package com.example.demo.voucher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public void add(Voucher voucher);

    public Optional<Voucher> get(UUID id);

    public Optional<ArrayList<Voucher>> getAll();
}
