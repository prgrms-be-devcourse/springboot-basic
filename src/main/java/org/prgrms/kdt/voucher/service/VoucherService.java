package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.dto.CreateVoucherRequest;
import org.prgrms.kdt.voucher.dto.VoucherResponse;
import org.prgrms.kdt.voucher.dto.VouchersResponse;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(CreateVoucherRequest request) {
        VoucherType voucherType = request.voucherType();
        double discountAmount = request.discountAmount();

        Voucher voucher = voucherRepository.insert(new Voucher(voucherType, voucherType.createPolicy(discountAmount)));
        return new VoucherResponse(voucher);
    }

    public VouchersResponse findAll() {
        return VouchersResponse.of(voucherRepository.findAll());
    }
}
