package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.dto.wallet.VoucherAssignRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final CustomerService customerService;
    private final VoucherService voucherService;

    @Transactional
    public VoucherAssignResponse assignVoucherToCustomer(VoucherAssignRequest voucherAssignRequest) {
        Voucher voucher = voucherService.findById(voucherAssignRequest.getVoucherId());
        Customer customer = customerService.findById(voucherAssignRequest.getCustomerId());
        voucherService.assignVoucherToCustomer(voucher, customer);

        return new VoucherAssignResponse(voucher.getId(), customer.getId());
    }
}
