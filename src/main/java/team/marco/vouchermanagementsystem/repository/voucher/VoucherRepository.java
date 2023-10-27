package team.marco.vouchermanagementsystem.repository.voucher;

import team.marco.vouchermanagementsystem.model.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
