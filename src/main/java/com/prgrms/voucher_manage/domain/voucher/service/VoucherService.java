package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.dto.UpdateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VoucherService{
    private final VoucherRepository voucherRepository;

    public void createVoucher(CreateVoucherDto dto) {
        voucherRepository.save(dto.of());
    }

    public List<Voucher> getVouchers() {
       return voucherRepository.findAll();
    }

    public Voucher findVoucher(UUID voucherId){
        return voucherRepository.findById(voucherId).orElseThrow(RuntimeException::new);
    }

    public void updateVoucher(UpdateVoucherDto dto){
        int update = voucherRepository.update(dto.of());
        if (update !=1) throw new RuntimeException("업데이트에 실패하였습니다");
    }

    public void deleteVoucher(UUID voucherId){
        int delete = voucherRepository.deleteById(voucherId);
        if (delete!=1) throw new RuntimeException("삭제에 실패했습니다.");
    }
}
