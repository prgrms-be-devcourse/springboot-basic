package org.promgrammers.springbootbasic.domain.voucher.service;

import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.promgrammers.springbootbasic.exception.EmptyVoucherListException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = VoucherFactory.createVoucher(createVoucherRequest);
        voucherRepository.insert(voucher);
        return new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    public Optional<VoucherResponse> findById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .map(voucher -> new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount()))
                .or(() -> {
                    logger.error("Invalid ID {} ", voucherId);
                    throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다. : " + voucherId);
                });
    }

    public VoucherListResponse findAll() {
        List<Voucher> voucherList = voucherRepository.findAll();

        if (voucherList.isEmpty()) {
            logger.error("Empty Voucher Repository");
            throw new EmptyVoucherListException("저장된 바우처가 없습니다.");
        }

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
