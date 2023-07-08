package com.example.voucher.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.voucher.domain.Voucher;

@Repository
public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

}
