package team.marco.vouchermanagementsystem.repository;

import team.marco.vouchermanagementsystem.model.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
