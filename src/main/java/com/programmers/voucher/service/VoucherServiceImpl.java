package com.programmers.voucher.service;

import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherType;
import com.programmers.voucher.voucher.VoucherValidator;
import com.programmers.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.programmers.message.ErrorMessage.VOUCHER_ID_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository, WalletRepository walletRepository) {
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public VoucherDto register(String voucherType, String value) {
        Voucher voucher = VoucherValidator.getValidateVoucher(voucherType, value);

        voucherRepository.registerVoucher(voucher);

        return entityToDto(voucher);
    }

    @Override
    public VoucherDto getVoucher(UUID voucherId) {
        Optional<Voucher> result = voucherRepository.findById(voucherId);

        Voucher voucher = result.orElseThrow(() ->
                new RuntimeException(VOUCHER_ID_NOT_FOUND.getMessage()));

        return entityToDto(voucher);
    }

    @Override
    public List<VoucherDto> findAll() {
        List<Voucher> vouchers = voucherRepository.findAllVouchers();

        return toDtoList(vouchers);
    }

    @Override
    public List<VoucherDto> searchVouchersByCustomerId(UUID customerId) {
        List<Voucher> vouchers = walletRepository.findVouchersByCustomerId(customerId);

        return toDtoList(vouchers);
    }

    @Override
    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteVoucher(voucherId);
    }

    @Override
    public List<VoucherDto> getTypeVoucher(String type) {
        VoucherType validateType = VoucherType.getValidateVoucherType(type);
        List<Voucher> vouchers = voucherRepository.findByType(validateType.name());

        return toDtoList(vouchers);
    }

    @Override
    public List<VoucherDto> findVoucherByPeriod(LocalDateTime from, LocalDateTime to) {
        List<Voucher> vouchers = voucherRepository.findByPeriod(from, to);

        return toDtoList(vouchers);
    }

    private List<VoucherDto> toDtoList(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(voucher -> entityToDto(voucher))
                .collect(Collectors.toList());
    }

}
