package org.prgms.voucheradmin.domain.voucher.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherReqDto;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherUpdateReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.global.exception.VoucherNotFoundException;
import org.prgms.voucheradmin.global.exception.WrongInputException;
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
    public Voucher createVoucher(VoucherReqDto voucherReqDto) throws IOException {
        Voucher voucher = getVoucherInstance(
                UUID.randomUUID(),
                voucherReqDto.getVoucherType(),
                voucherReqDto.getAmount());

        return voucherRepository.create(voucher);
    }

    @Transactional(readOnly = true)
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> new VoucherNotFoundException(voucherId));
    }

    /**
     * 바우처 목록을 조회하는 메서드 입니다.
     **/
    @Transactional(readOnly = true)
    public List<Voucher> getVouchers() throws IOException{
        return voucherRepository.findAll();
    }

    /**
     * 바우처의 type, amount(percent)를 수정하는 메서드입니다.
     **/
    @Transactional
    public Voucher updateVoucher(UUID voucherId, VoucherReqDto voucherReqDto) {
        Voucher retrievedVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));

        Voucher updatedVoucher = getVoucherInstance(retrievedVoucher.getVoucherId(), voucherReqDto.getVoucherType(), voucherReqDto.getAmount());
        return voucherRepository.update(updatedVoucher);
    }

    /**
     * 바우처를 삭제하는 메서드입니다.
     **/
    @Transactional
    public void deleteVoucher(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new VoucherNotFoundException(voucherId));
        voucherRepository.delete(voucher);
    }

    /**
     * 바우처의 종류에 따라 알맞은 Voucehr를 반환 하는 메서드입니다.
     **/
    private Voucher getVoucherInstance(UUID voucherId, VoucherType voucherType, long amount) {
        switch (voucherType) {
            case FIXED_AMOUNT:
                return new FixedAmountVoucher(voucherId, amount);
            case PERCENTAGE_DISCOUNT:
                return new PercentageDiscountVoucher(voucherId, (int)amount);
            default:
                throw new WrongInputException();
        }
    }
}
