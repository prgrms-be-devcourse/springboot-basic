package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.dto.VoucherServiceRequestDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {

    Optional<VoucherResponseDTO> getVoucherById(UUID voucherId);

    List<VoucherResponseDTO> getAllVoucherDTOs();

    void updateVoucherEntity(VoucherServiceRequestDTO voucher);

    Voucher createVoucher(VoucherServiceRequestDTO voucher);

    void delete(UUID voucherId);

    List<VoucherResponseDTO> getVouchersByType(VoucherType voucherType);

}
