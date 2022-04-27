package com.example.voucherproject.voucher.repository;
import com.example.voucherproject.voucher.enums.VoucherType;
import com.example.voucherproject.voucher.domain.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    //create
    Voucher insert(Voucher voucher);

    //read
    List<Voucher> findHavingTypeAll(VoucherType type);
    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    //update

    //delete
    int deleteAll();

    long count();
}
