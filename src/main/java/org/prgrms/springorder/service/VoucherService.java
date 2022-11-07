package org.prgrms.springorder.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.repository.VoucherRepository;
import org.prgrms.springorder.request.VoucherCreateRequest;
import org.prgrms.springorder.response.VoucherResponse;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherCreateRequest request) {
        Voucher voucher = VoucherFactory.create(request);
        return voucherRepository.insert(voucher).getVoucherId();
    }

    public List<VoucherResponse> findAll() {
        return voucherRepository.findAll().stream()
            .map(
                voucher -> new VoucherResponse(
                    voucher.getVoucherId(),
                    voucher.getAmount(),
                    voucher.getVoucherType()))
            .collect(Collectors.toList());
    }

}