package com.example.demo.service;

import com.example.demo.domain.voucher.FixedAmountVoucher;
import com.example.demo.domain.voucher.PercentDiscountVoucher;
import com.example.demo.domain.voucher.Voucher;
import com.example.demo.dto.VoucherDto;
import com.example.demo.enums.VoucherDiscountType;
import com.example.demo.repository.voucher.VoucherRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherDto save(VoucherDiscountType voucherDiscountType, int amount) {
        Voucher voucher = switch (voucherDiscountType) {
            case FIX -> new FixedAmountVoucher(amount);
            case PERCENT -> new PercentDiscountVoucher(amount);
        };
        voucherRepository.save(voucher);
        return VoucherDto.from(voucher);
    }

    public VoucherDto read(UUID id) {
        return VoucherDto.from(voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("바우처 read 오류 : 입력 된 id에 해당하는 바우처가 존재하지 않습니다. 입력 받은 id : %s ", id.toString()))));
    }

    public List<VoucherDto> readList() {
        List<Voucher> voucherList = voucherRepository.findAll();

        return voucherList.stream()
                .map(VoucherDto::from)
                .toList();
    }

    public void updateAmount(UUID id, int discountAmount) {
        if (voucherRepository.notExistById(id)) {
            throw new IllegalArgumentException(String.format("바우처 amount 업데이트 오류 : 입력 된 id에 해당하는 바우처가 존재하지 않습니다. 입력 받은 id : %s ", id.toString()));
        }

        voucherRepository.updateAmount(id, discountAmount);
    }

    public void delete(UUID id) {
        if (voucherRepository.notExistById(id)) {
            throw new IllegalArgumentException(String.format("바우처 삭제 오류 : 입력 된 id에 해당하는 바우처가 존재하지 않습니다. 입력 받은 id : %s ", id.toString()));
        }

        voucherRepository.deleteById(id);
    }
}
