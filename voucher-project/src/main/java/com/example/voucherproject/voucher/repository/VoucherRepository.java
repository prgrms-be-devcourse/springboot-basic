package com.example.voucherproject.voucher.repository;
import com.example.voucherproject.voucher.domain.Voucher;
import java.util.List;

public interface VoucherRepository {
    List<Voucher> getList();
    Voucher save(Voucher voucher);
}
