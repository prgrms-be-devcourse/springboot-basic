package com.prgms.VoucherApp.domain.voucher.model;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateReqDto;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResDto;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResDto;
import com.prgms.VoucherApp.domain.voucher.storage.VoucherStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherDao {

    private final Logger logger = LoggerFactory.getLogger(VoucherDao.class);
    private final VoucherStorage voucherStorage;

    public VoucherDao(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    @Transactional
    public void save(VoucherCreateReqDto requestDto) {
        Voucher voucher = switch (requestDto.getVoucherType()) {
            case FIXED_VOUCHER ->
                new FixedAmountVoucher(UUID.randomUUID(), requestDto.getAmount(), requestDto.getVoucherType());
            case PERCENT_VOUCHER ->
                new PercentDiscountVoucher(UUID.randomUUID(), requestDto.getAmount(), requestDto.getVoucherType());
        };
        voucherStorage.save(voucher);
        logger.debug("저장된 할인권의 정보 : id : [{}], amount : [{}], type : [{}]",
            voucher.getVoucherId(), voucher.getDiscountAmount(), voucher.getVoucherType());
    }

    public Optional<VoucherResDto> findOne(UUID id) {
        return voucherStorage.findByVoucherId(id)
            .map(VoucherResDto::new);
    }

    public VouchersResDto findAll() {
        List<Voucher> findVouchers = voucherStorage.findAll();

        List<VoucherResDto> convertVoucherResDto = findVouchers.stream()
            .map(VoucherResDto::new)
            .toList();

        return new VouchersResDto(convertVoucherResDto);
    }
}
