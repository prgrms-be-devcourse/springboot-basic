package org.prgrms.orderApp.voucher;


import org.prgrms.orderApp.error.CustomRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.orderApp.error.RestApiErrorCode.*;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherDto saveVoucher(VoucherDto voucherDto) {
        var insertVoucher = new Voucher(
                voucherDto.getName(),
                voucherDto.getAmount(),
                voucherDto.getVoucherType(),
                voucherDto.getExpireAt()
        );

        if (insertVoucher.checkPercentVoucherOver100()) throw new CustomRuntimeException(PERCENT_VOUCHER_OVER_100);
        if (voucherRepository.insert(insertVoucher) != 1) throw new CustomRuntimeException(FAIL_SAVE_VOUCHER);



        return voucherRepository.findById(insertVoucher.getVoucherId())
                .map(v -> new VoucherDto(
                        v.getVoucherId(),
                        v.getAmount(),
                        v.getAvailable(),
                        v.getVoucherType(),
                        v.getName(),
                        v.getExpireAt(),
                        v.getCreatedAt(),
                        v.getCustomerCount()
                )).orElseThrow( () -> new CustomRuntimeException(FAIL_SAVE_VOUCHER) );
    }

    public List<VoucherDto> getAllVouchers() {
        return voucherRepository.findAll()
                .stream()
                .sorted(
                        Comparator.comparing(Voucher::getCreatedAt))
                .map(v -> new VoucherDto(
                        v.getVoucherId(),
                        v.getAmount(),
                        v.getAvailable(),
                        v.getVoucherType(),
                        v.getName(),
                        v.getExpireAt(),
                        v.getCreatedAt(),
                        v.getCustomerCount())
                ).toList();
    }


    public VoucherDto getVoucherById(UUID voucherId){
        return voucherRepository.findById(voucherId)
                .map(v -> new VoucherDto(
                        v.getVoucherId(),
                        v.getAmount(),
                        v.getAvailable(),
                        v.getVoucherType(),
                        v.getName(),
                        v.getExpireAt(),
                        v.getCreatedAt(),
                        v.getCustomerCount()
                ))
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUNT_VOUCHER_ID));
    }

    public UUID deleteById(UUID voucherId){
        if (voucherRepository.deleteById(voucherId) == 0) throw new CustomRuntimeException(FAIL_DELETE_BY_ID);
        return voucherId;
    }

    public List<Voucher> getAllVoucherByType(String dataType){
        return voucherRepository.findAllByType(dataType);
    }

    public List<Voucher> getAllVoucherByCreateDate(LocalDateTime fromDate, LocalDateTime toDate){
        return voucherRepository.findAllByCreatedDate(fromDate, toDate);
    }


}

