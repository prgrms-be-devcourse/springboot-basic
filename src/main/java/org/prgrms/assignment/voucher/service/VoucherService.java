package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher getVoucherById(UUID voucherId);

    List<VoucherDTO> convertToVoucherDTOs();

    void update(Voucher voucher);

    Voucher createVoucher(VoucherType voucherType, long benefit, long durationDate);

}
