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
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
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

    public List<Voucher> getVouchersByVoucherType(VoucherType type) {
        logger.info("Starts getVouchersByVoucherType()");
        return voucherRepository
                .findByVoucherType(type);
    }

    public List<Voucher> getVoucherByCreatedAt(String startDate, String endDate) {
        logger.info("Starts getVoucherByCreatedAt()");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return voucherRepository.findByVoucherTerm(LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter));
    }

    @Transactional
    public void deleteVoucher(UUID voucherId, UUID customerId) {
        voucherRepository.deleteVoucher(voucherId, customerId);
    }

    private boolean checkValidity(VoucherSaveRequestDto voucherSaveRequestDto, UUID uuid) {
        logger.info("Starts checkValidity()");
        if(voucherSaveRequestDto.getVoucherType() == VoucherType.UNDEFINED) {
            logger.warn("Fail to create a voucher.");
            return false;
        }

        if(voucherSaveRequestDto.getDiscount() < 0) {
            logger.warn("Fail to create a voucher.");
            return false;
        }

        if(voucherRepository.findById(uuid).isPresent()) {
            logger.warn("Voucher is duplicated");
            return false;
        }

        logger.info("Succeed to create a voucher.");
        return true;
    }
}