package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.service.dto.ServiceCreateVoucherRequest;
import org.prgrms.kdt.voucher.service.dto.VoucherResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.mapper.ServiceVoucherMapper;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final ServiceVoucherMapper mapper;

    public VoucherService(VoucherRepository voucherRepository, ServiceVoucherMapper mapper) {
        this.voucherRepository = voucherRepository;
        this.mapper = mapper;
    }

    public VoucherResponse createVoucher(ServiceCreateVoucherRequest request) {
        Voucher voucher = voucherRepository.insert(mapper.serviceDtoToVoucher(request));
        return new VoucherResponse(voucher);
    }

    public VoucherResponses findAll() {
        return VoucherResponses.of(voucherRepository.findAll());
    }
}
