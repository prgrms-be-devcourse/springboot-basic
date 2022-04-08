package com.example.demo.voucher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class FileVoucherRepository implements VoucherRepository{
    @Override
    public void add(Voucher voucher) {

    }

    @Override
    public Optional<Voucher> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<Voucher>> getAll() {
        return Optional.empty();
    }
}
