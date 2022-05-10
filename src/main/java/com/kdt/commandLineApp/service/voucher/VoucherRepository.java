package com.kdt.commandLineApp.service.voucher;

import org.springframework.beans.factory.DisposableBean;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends DisposableBean {
    public void add(Voucher voucher);

    public Optional<Voucher> get(long id);

    public List<Voucher> getAll(int page, int size, String type);

    public void remove(long id);

    public void deleteAll();
}
