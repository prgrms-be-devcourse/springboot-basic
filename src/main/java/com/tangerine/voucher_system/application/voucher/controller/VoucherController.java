package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VoucherController {
    ResponseEntity<VoucherResponse> createVoucher(CreateVoucherRequest request);

    ResponseEntity<VoucherResponse> updateVoucher(UpdateVoucherRequest request);

    ResponseEntity<List<VoucherResponse>> voucherList();

    ResponseEntity<VoucherResponse> voucherById(UUID voucherId);

    ResponseEntity<List<VoucherResponse>> voucherByCreatedAt(LocalDate createdAt);

    ResponseEntity<VoucherResponse> deleteVoucherById(UUID voucherId);
}
