package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.mapper.VoucherMapper;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:27 오전
 */
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<VoucherDto> getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .map(VoucherMapper::voucherToVoucherDto);
    }
}
