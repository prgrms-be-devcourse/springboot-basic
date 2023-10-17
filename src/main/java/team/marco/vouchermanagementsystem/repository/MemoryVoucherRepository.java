package team.marco.vouchermanagementsystem.repository;

import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.Voucher;

import java.util.List;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    @Override
    public void save(Voucher voucher) {

    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
