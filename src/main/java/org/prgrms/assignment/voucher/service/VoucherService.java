package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {

    Optional<VoucherResponseDTO> getVoucherById(UUID voucherId);

    List<VoucherResponseDTO> getAllVoucherDTOs();

    void updateVoucherEntity(Voucher voucher);

    Voucher createVoucher(VoucherType voucherType, long benefit, long durationDate);

    void delete(UUID voucherId);

    List<VoucherResponseDTO> getVouchersByType(VoucherType voucherType);

}
