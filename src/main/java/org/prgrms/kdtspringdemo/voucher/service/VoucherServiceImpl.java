package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.exception.VoucherIdNotFoundException;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponse;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.*;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public VoucherResponse create(VoucherType type, long amount) {
        Voucher voucher = type.createVoucher(amount);
        Voucher savedVoucher = voucherRepository.save(voucher);

        return VoucherResponse.toDto(savedVoucher.getId(), savedVoucher.getType(), savedVoucher.getAmount());
    }

    @Override
    public VoucherResponse findById(UUID id) {
        Optional<Voucher> foundVoucher = voucherRepository.findById(id);
        Voucher voucher = foundVoucher.orElseThrow(() -> new VoucherIdNotFoundException(VOUCHER_ID_LOOKUP_FAILED));

        return VoucherResponse.toDto(voucher.getId(), voucher.getType(), voucher.getAmount());
    }

    @Override
    public List<VoucherResponse> findAll() {
        return voucherRepository.findAll()
                .stream()
                .map(v -> new VoucherResponse(v.getId(), v.getType(), v.getAmount()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public VoucherResponse update(UUID id, VoucherType type, long amount) {
        voucherRepository.update(id, type, amount);
        Optional<Voucher> checkingVoucher = voucherRepository.findById(id);
        Voucher voucher = checkingVoucher.orElseGet(() -> voucherRepository.save(type.updateVoucher(id, amount)));

        return VoucherResponse.toDto(voucher.getId(), voucher.getType(), voucher.getAmount());
    }

    @Override
    public void delete(UUID id) {
        voucherRepository.deleteById(id);
    }
}
