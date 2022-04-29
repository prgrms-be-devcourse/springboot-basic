package com.example.voucherproject.voucher.repository;
import com.example.voucherproject.voucher.dto.VoucherDTO;
import com.example.voucherproject.voucher.model.VoucherType;
import com.example.voucherproject.voucher.model.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);
    List<Voucher> findAllByVoucherType(VoucherType type);
    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);
    int deleteAll();
    long count();
    int deleteById(UUID id);
    List<Voucher> findByTypeAndDate(VoucherDTO.Query query);
    void updateTypeAndAmountByDto(VoucherDTO.Update update);
}
