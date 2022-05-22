package org.prgms.voucheradmin.domain.voucher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherCondition;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.global.exception.customexception.VoucherNotFoundException;
import org.springframework.stereotype.Service;

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
    public Voucher createVoucher(VoucherReqDto voucherReqDto) {
        Voucher voucher = voucherReqDto.getVoucherType().createVoucher(UUID.randomUUID(), voucherReqDto.getAmount(), LocalDateTime.now());
        return voucherRepository.create(voucher);
    }
    /**
     * 바우처 목록을 조회하는 메서드 입니다.
     **/
    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersWithCondition(VoucherCondition voucherCondition) {
        if(voucherCondition.hasVoucherType() && !voucherCondition.hasVoucherDateRage()) {
            return voucherRepository.findAllWithVoucherType(voucherCondition.getVoucherType());
        }else if(!voucherCondition.hasVoucherType() && voucherCondition.hasVoucherDateRage()) {
            return voucherRepository.findAllWithDate(voucherCondition.getFrom(), voucherCondition.getTo());
        }else{
            return voucherRepository.findAllWithVoucherTypeAndDate(
                    voucherCondition.getVoucherType(),
                    voucherCondition.getFrom(),
                    voucherCondition.getTo());
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> new VoucherNotFoundException(voucherId));
    }

    /**
     * 바우처의 type, amount(percent)를 수정하는 메서드입니다.
     **/
    public Voucher updateVoucher(UUID voucherId, VoucherReqDto voucherReqDto) {
        Voucher retrievedVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));

        Voucher updatedVoucher = voucherReqDto.getVoucherType().createVoucher(retrievedVoucher.getVoucherId(), voucherReqDto.getAmount(), retrievedVoucher.getCreatedAt());
        return voucherRepository.update(updatedVoucher);
    }

    /**
     * 바우처를 삭제하는 메서드입니다.
     **/
    public void deleteVoucher(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new VoucherNotFoundException(voucherId));
        voucherRepository.delete(voucher);
    }
}
