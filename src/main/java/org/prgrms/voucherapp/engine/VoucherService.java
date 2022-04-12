package org.prgrms.voucherapp.engine;

import org.prgrms.voucherapp.exception.NullVoucherException;
import org.prgrms.voucherapp.exception.WrongAmountException;
import org.prgrms.voucherapp.global.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) throws NullVoucherException {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NullVoucherException(MessageFormat.format("{0}는 존재하지 않는 바우처 id입니다.", voucherId)));
    }

    public Voucher createVoucher(VoucherType type, UUID uuid, long amount) {
        while (voucherRepository.findById(uuid).isPresent()) {
            uuid = UUID.randomUUID();
        }
        Voucher voucher = VoucherType.createVoucher(type, uuid, amount);
        return voucherRepository.insert(voucher);
    }

    public String getVoucherListByStr() {
        StringBuilder sb = new StringBuilder();
        for (Voucher voucher : voucherRepository.getVoucherAll()) {
            sb.append(voucher.toString()).append("\n");
        }
        if(sb.isEmpty()) return "Voucher Repository is empty.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

}
