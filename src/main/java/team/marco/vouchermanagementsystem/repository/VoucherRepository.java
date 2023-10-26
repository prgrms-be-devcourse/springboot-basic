package team.marco.vouchermanagementsystem.repository;

import java.util.List;
import team.marco.vouchermanagementsystem.model.Voucher;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
