package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.dto.VoucherSaveRequestDto;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.helper.MessageHelper;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final MessageHelper messageHelper = new MessageHelper();

    public VoucherService(@Qualifier("fileVoucher") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> createVoucher(VoucherSaveRequestDto voucherSaveRequestDto) {
        UUID uuid = UUID.randomUUID();
        if(!checkValidity(voucherSaveRequestDto, uuid)) {
            return Optional.empty();
        }

        if(voucherSaveRequestDto.getVoucherType() == VoucherType.FIXED) {
            return Optional.of(voucherRepository.save(new FixedAmountVoucher(uuid, voucherSaveRequestDto.getDiscount())));
        } else {
            return Optional.of(voucherRepository.save(new PercentDiscountVoucher(uuid, voucherSaveRequestDto.getDiscount())));
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()-> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository
                .findAll();
    }

    private boolean checkValidity(VoucherSaveRequestDto voucherSaveRequestDto, UUID uuid) {
        if(voucherSaveRequestDto.getVoucherType() == VoucherType.UNDEFINED) {
            messageHelper.showRetryMessage();
            return false;
        }

        messageHelper.showEnterVoucherDiscount();
        if(voucherSaveRequestDto.getDiscount() < 0) {
            messageHelper.showRetryMessage();
            return false;
        }

        if(voucherRepository.findById(uuid).isPresent()) {
            messageHelper.showDuplicateVoucherMessage();
            return false;
        }

        return true;
    }
}