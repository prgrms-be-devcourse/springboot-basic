package org.prgms.voucheradmin.domain.voucher.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherCreateReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 바우처의 생성과 바우처 조회 로직을 담당하는 클래스 입니다.
*/
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * 바우처의 생성을 담당하는 메서드입니다.
    */
    @Transactional
    public Voucher createVoucher(VoucherCreateReqDto voucherCreateReqDto) throws IOException {
        Voucher voucher = getVoucherInstance(voucherCreateReqDto);
        return voucherRepository.create(voucher);
    }

    /**
     * 바우처 목록을 조회하는 메서드 입니다
     **/
    public List<Voucher> getVouchers() throws IOException{
        return voucherRepository.findAll();
    }

    /**
     * 바우처의 종류에 따라 알맞은 Voucehr를 반환 하는 메서드입니다.
     **/
    private Voucher getVoucherInstance(VoucherCreateReqDto voucherCreateReqDto) {
        VoucherType voucherType = voucherCreateReqDto.getVoucherType();
        UUID voucherId = UUID.randomUUID();

        switch (voucherType) {
            case FIXED_AMOUNT:
                long amount = voucherCreateReqDto.getAmount();
                return new FixedAmountVoucher(voucherId, amount);
            default:
                long percent = voucherCreateReqDto.getAmount();
                return new PercentageDiscountVoucher(voucherId, percent);
        }
    }
}
