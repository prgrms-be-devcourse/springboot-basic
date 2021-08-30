package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.dto.VoucherSaveRequestDto;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.helper.MessageHelper.*;

@Service
public class VoucherService {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> createVoucher(VoucherSaveRequestDto voucherSaveRequestDto) {
        logger.info("Starts createVoucher()");

        UUID uuid = UUID.randomUUID();

        logger.info("uuid : {}", uuid.toString());
        logger.info("User input : {}", voucherSaveRequestDto.getVoucherType());
        logger.info("User input : {}", voucherSaveRequestDto.getDiscount());

        if(!checkValidity(voucherSaveRequestDto, uuid)) {
            return Optional.empty();
        }

        if(voucherSaveRequestDto.getVoucherType() == VoucherType.FIXED) {
            logger.info("VoucherType is FixedAmountVoucher");
            return voucherRepository.save(new FixedAmountVoucher(voucherSaveRequestDto.getCustomerId(), uuid, voucherSaveRequestDto.getDiscount(), VoucherType.FIXED));
        } else {
            logger.info("VoucherType is PercentDiscountVoucher");
            return voucherRepository.save(new PercentDiscountVoucher(voucherSaveRequestDto.getCustomerId(), uuid, voucherSaveRequestDto.getDiscount(), VoucherType.DISCOUNT));
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        logger.info("Starts getVoucher(), UUID : {}", voucherId.toString());
        return voucherRepository
                .findById(voucherId)
               .orElseThrow(()-> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVouchers() {
        logger.info("Starts getAllVouchers()");
        return voucherRepository
                .findAll();
    }

    public List<Voucher> getVouchersByCustomerId(UUID customerId) {
        logger.info("Starts getVouchersByCustomerId()");
        return voucherRepository
                .findByVoucherId(customerId);
    }

    private boolean checkValidity(VoucherSaveRequestDto voucherSaveRequestDto, UUID uuid) {
        logger.info("Starts checkValidity()");
        if(voucherSaveRequestDto.getVoucherType() == VoucherType.UNDEFINED) {
            logger.warn("Fail to create a voucher.");
            showRetryMessage();
            return false;
        }

        showEnterVoucherDiscount();
        if(voucherSaveRequestDto.getDiscount() < 0) {
            logger.warn("Fail to create a voucher.");
            showRetryMessage();
            return false;
        }

        if(voucherRepository.findById(uuid).isPresent()) {
            logger.warn("Voucher is duplicated");
            showDuplicateVoucherMessage();
            return false;
        }

        logger.info("Succeed to create a voucher.");
        return true;
    }
}