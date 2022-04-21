package org.prgrms.voucherapp.engine.voucher.service;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.repository.VoucherRepository;
import org.prgrms.voucherapp.exception.NullVoucherException;
import org.prgrms.voucherapp.global.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;


/*
* VoucherService
* Q. service layer에 정의해야하는 함수에 대한 개념이 모호합니다.
* 'Navigator에서 사용하기 편하도록 service는 repository의 데이터를 가지고 가공한다.'라고 이해해도 될까요?
* 예를 들어 getVoucherListByStr 같은 경우, 그냥 getVoucherList로 정의하고,Repository에서 반환된 ArrayList<Voucher>를 그대로 반환한뒤,
* output에서 ArrayList<Voucher>를 가공하는 선택지도 있었습니다. 하지만 service는 말그대로 service니까 편하게 가공해서 주자라고 결정했습니다.
* 만약에 개발을 하다 repository에서 가져온 데이터가 가공할 필요가 없다면 service에서 아무 역할 없이 그냥 반환하는 경우도 많나요?
* */
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
        Voucher voucher = type.createVoucher(uuid, amount);
        return voucherRepository.insert(voucher);
    }

    /*
    * getVoucherListByStr : voucherRepository에 있는 모든 voucher들을 string으로 만들어 프린트합니다.
    * */
    public String getVoucherListByStr() {
        StringBuilder sb = new StringBuilder();
        for (Voucher voucher : voucherRepository.getVoucherAll()) {
            sb.append(voucher.toString()).append("\n");
        }
        if (sb.isEmpty()) return "Voucher Repository is empty.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

}
