package com.prgms.VoucherApp.domain.voucher.model;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateReqDto;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResDto;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherUpdateReqDto;
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

    public VouchersResDto findByVoucherType(VoucherType voucherType) {
        List<Voucher> findVouchers = voucherStorage.findByVoucherType(voucherType);

        List<VoucherResDto> convertVoucherResDto = findVouchers.stream()
            .map(VoucherResDto::new)
            .toList();

        return new VouchersResDto(convertVoucherResDto);
    }

    @Transactional
    public void update(VoucherUpdateReqDto voucherUpdateReqDto) {
        Voucher voucher = switch (voucherUpdateReqDto.getVoucherType()) {
            case FIXED_VOUCHER ->
                new FixedAmountVoucher(voucherUpdateReqDto.getId(), voucherUpdateReqDto.getAmount(), voucherUpdateReqDto.getVoucherType());
            case PERCENT_VOUCHER ->
                new PercentDiscountVoucher(voucherUpdateReqDto.getId(), voucherUpdateReqDto.getAmount(), voucherUpdateReqDto.getVoucherType());
        };

        voucherStorage.update(voucher);
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherStorage.deleteById(id);
    }
}
