package org.prgrms.voucherapp.engine;

import org.prgrms.voucherapp.exception.NullVoucherException;
import org.prgrms.voucherapp.global.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) throws NullVoucherException {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()->new NullVoucherException(MessageFormat.format("{0}는 존재하지 않는 바우처 id입니다.", voucherId)));
    }

    public Voucher createVoucher(VoucherType type, UUID uuid, long amount){
        //TODO : validate amount
        //TODO : validate uuid
        return VoucherType.createVoucher(type, uuid, amount);
    }

    public ArrayList<Voucher> getVoucherAll(){

    }

}
