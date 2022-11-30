package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.*;
import org.prgrms.kdt.storage.JdbcVoucherStorage;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherProvider.getVoucher;

@Service
public class VoucherWebProvider {

    private final JdbcVoucherStorage jdbcVoucherStorage;


    public VoucherWebProvider(JdbcVoucherStorage jdbcVoucherStorage) {
        this.jdbcVoucherStorage = jdbcVoucherStorage;
    }

    public Voucher createVoucher(String voucherType, Integer amount) {
        VoucherType type;
        try{
            type = VoucherType.findVoucherTypeByInput(voucherType);
        } catch (InvalidITypeInputException invalidType){
            throw new GetResultFailedException("바우처 생성에 실패하였습니다. 실패 원인 -> 바우처 타입이 올바르지 않음.", invalidType);
        }

        Voucher newVoucher = getVoucher(type, UUID.randomUUID().toString(), amount);

        jdbcVoucherStorage.save(newVoucher);

        return newVoucher;
    }

    public List<Voucher> getVoucherList() {
        return jdbcVoucherStorage.findAll();
    }

    public Voucher findVoucher(String voucherId) {
        return jdbcVoucherStorage.findById(voucherId)
                .orElseThrow(() ->
                        new NoVoucherException(
                                MessageFormat.format("해당 ID [{0}]로 바우처를 찾아올 수 없습니다.", voucherId)));
    }

    public void deleteVoucher(String voucherId){
        jdbcVoucherStorage.deleteById(voucherId);
    }

}
