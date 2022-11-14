package org.prgrms.springorder.domain.voucher.service;

import java.util.List;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.voucher.api.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherCreateRequest request) {
        Voucher voucher = VoucherFactory.create(request); // 빈으로 관리되게 해볼까?
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public List<String> findAllConvertedToString() {
        return findAll().stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    }

}