package com.kdt.commandLineApp.voucher;

import org.springframework.beans.factory.DisposableBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository extends DisposableBean {
    public void add(Voucher voucher);

    public Optional<Voucher> get(String id);

    public List<Voucher> getType(String type);

    public List<Voucher> getAll();

    public void remove(String id);

    public void deleteAll();
}
