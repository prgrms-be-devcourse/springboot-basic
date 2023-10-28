package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.wallet.VoucherAssignRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignResponse;
import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final CustomerService customerService;
    private final VoucherService voucherService;

    @Transactional
    public VoucherAssignResponse assignVoucherToCustomer(VoucherAssignRequest voucherAssignRequest) {
        Voucher voucher = voucherService.findById(voucherAssignRequest.getVoucherId());
        Customer customer = customerService.findById(voucherAssignRequest.getCustomerId());
        Voucher updatedVoucher = voucherService.assignVoucherToCustomer(voucher, customer);

        return new VoucherAssignResponse(updatedVoucher.getId(), updatedVoucher.getCustomerId());
    }

    public List<VoucherFindResponse> findVouchersByCustomerId(UUID customerId) {
        Customer customer = customerService.findById(customerId);
        return voucherService.findVouchersByCustomer(customer);
    }

    @Transactional
    public VoucherAssignResponse unassignVoucherFromCustomer(UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        if (!voucher.isAssigned()) {
            throw VoucherException.of(VoucherErrorMessage.NOT_ASSIGNED);
        }
        Customer customer = customerService.findById(voucher.getCustomerId());
        Voucher updatedVoucher = voucherService.unassignVoucherToCustomer(voucher);

        return new VoucherAssignResponse(updatedVoucher.getId(), customer.getId());
    }

    public CustomerFindResponse findCustomerByVoucherId(UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        if (!voucher.isAssigned()) {
            throw VoucherException.of(VoucherErrorMessage.NOT_ASSIGNED);
        }
        Customer customer = customerService.findById(voucher.getCustomerId());

        return new CustomerFindResponse(customer);
    }
}
