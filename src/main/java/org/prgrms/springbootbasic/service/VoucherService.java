package org.prgrms.springbootbasic.service;

import org.prgrms.springbootbasic.message.Request;
import org.prgrms.springbootbasic.message.Response;
import org.prgrms.springbootbasic.type.TypeOption;
import org.prgrms.springbootbasic.factory.VoucherFactory;
import org.prgrms.springbootbasic.repository.VoucherRepository;
import org.prgrms.springbootbasic.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final Map<String, VoucherFactory> voucherFactoryMap;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, Map<String, VoucherFactory> voucherFactoryMap) {
        this.voucherRepository = voucherRepository;
        this.voucherFactoryMap = voucherFactoryMap;
    }

    public Response process(Request request) {
        Response response = new Response(request.getMenuItem());

        if (request.isCreateRequest()) {
            Voucher voucher = createVoucher(request.getOption(), request.getArgument("quantity"));
            voucherRepository.insert(voucher);
            response.addVoucherList(voucher);
        } else if (request.isLookupRequest()) {
            Voucher[] vouchers = lookupVoucherList();
            response.addVoucherList(vouchers);
        }
        return response;
    }

    private Voucher createVoucher(TypeOption option, Long quantity) {
        VoucherFactory voucherFactory = voucherFactoryMap.get(option.getBeanName());
        return voucherFactory.createVoucher(quantity);
    }

    private Voucher[] lookupVoucherList() {
        return voucherRepository.findAll().values().toArray(Voucher[]::new);
    }

    // test
    public Map<String, VoucherFactory> getVoucherFactoryMap() {
        return voucherFactoryMap;
    }
}
