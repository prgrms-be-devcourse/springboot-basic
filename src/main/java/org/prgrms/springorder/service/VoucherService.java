package org.prgrms.springorder.service;

import java.util.List;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.repository.VoucherRepository;
import org.prgrms.springorder.request.VoucherCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherCreateRequest request) {
        Voucher voucher = VoucherFactory.create(request);
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

}