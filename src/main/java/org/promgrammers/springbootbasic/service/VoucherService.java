package org.promgrammers.springbootbasic.service;

import org.promgrammers.springbootbasic.domain.Voucher;
import org.promgrammers.springbootbasic.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = VoucherFactory.createVoucher(createVoucherRequest);
        voucherRepository.insert(voucher);
        return new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    public Optional<VoucherResponse> findById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .map(voucher -> new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount()))
                .or(() -> {
                    throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다. : " + voucherId);
                });
    }

    public VoucherListResponse findAll() {
        List<VoucherResponse> voucherResponseList = voucherRepository.findAll()
                .stream()
                .map(voucher -> new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount()))
                .toList();
        return new VoucherListResponse(voucherResponseList);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
