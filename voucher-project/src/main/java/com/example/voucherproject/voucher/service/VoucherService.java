package com.example.voucherproject.voucher.service;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.voucher.dto.VoucherDTO;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {
    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    boolean deleteById(UUID id);

    List<Voucher> findByTypeAndDate(VoucherDTO.Query query);

    Voucher createVoucher(VoucherDTO.Create form);

    void updateDTO(VoucherDTO.Update dto);
}
