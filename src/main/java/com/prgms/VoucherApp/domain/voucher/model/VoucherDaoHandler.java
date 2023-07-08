package com.prgms.VoucherApp.domain.voucher.model;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateReqDto;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResDto;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherUpdateReqDto;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherDaoHandler {

    private final Logger logger = LoggerFactory.getLogger(VoucherDaoHandler.class);
    private final VoucherDao voucherDao;

    public VoucherDaoHandler(VoucherDao voucherDao) {
        this.voucherDao = voucherDao;
    }

    @Transactional
    public void save(VoucherCreateReqDto requestDto) {
        Voucher voucher = switch (requestDto.getVoucherType()) {
            case FIXED_VOUCHER -> new FixedAmountVoucher(UUID.randomUUID(), requestDto.getAmount());
            case PERCENT_VOUCHER -> new PercentDiscountVoucher(UUID.randomUUID(), requestDto.getAmount());
        };
        voucherDao.save(voucher);
    }

    public Optional<VoucherResDto> findOne(UUID id) {
        return voucherDao.findById(id)
            .map(VoucherResDto::new);
    }

    public VouchersResDto findAll() {
        List<Voucher> findVouchers = voucherDao.findAll();

        List<VoucherResDto> convertVoucherResDto = findVouchers.stream()
            .map(VoucherResDto::new)
            .toList();

        return new VouchersResDto(convertVoucherResDto);
    }

    public VouchersResDto findByVoucherType(VoucherType voucherType) {
        List<Voucher> findVouchers = voucherDao.findByVoucherType(voucherType);

        List<VoucherResDto> convertVoucherResDto = findVouchers.stream()
            .map(VoucherResDto::new)
            .toList();

        return new VouchersResDto(convertVoucherResDto);
    }

    @Transactional
    public void update(VoucherUpdateReqDto voucherUpdateReqDto) {
        Voucher voucher = switch (voucherUpdateReqDto.getVoucherType()) {
            case FIXED_VOUCHER -> new FixedAmountVoucher(voucherUpdateReqDto.getId(), voucherUpdateReqDto.getAmount());
            case PERCENT_VOUCHER ->
                new PercentDiscountVoucher(voucherUpdateReqDto.getId(), voucherUpdateReqDto.getAmount());
        };

        voucherDao.update(voucher);
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherDao.deleteById(id);
    }
}
