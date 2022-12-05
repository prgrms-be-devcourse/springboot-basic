package org.prgrms.springorder.domain.customer.api;

import java.util.List;
import org.prgrms.springorder.console.io.ListResponse;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.console.io.StringResponse;
import org.prgrms.springorder.domain.voucher_wallet.model.Wallet;
import org.prgrms.springorder.domain.customer.api.request.CreateCustomerRequest;
import org.prgrms.springorder.domain.customer.api.request.CustomerIdRequest;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.service.CustomerService;
import org.prgrms.springorder.domain.voucher.api.request.AllocateVoucherRequest;
import org.prgrms.springorder.domain.voucher.api.request.DeleteVoucherRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Controller
@Profile("dev")
public class CustomerController  {

    private final CustomerService customerService;

    public CustomerController(
        CustomerService customerService) {
        this.customerService = customerService;
    }

    public Response findAllVouchers(CustomerIdRequest customerIdRequest) {
        Wallet wallet = customerService.findAllVouchers(customerIdRequest.getCustomerId());
        return new StringResponse(wallet);
    }

    public Response deleteVoucher(DeleteVoucherRequest deleteVoucherRequest) {
        customerService.deleteVoucher(deleteVoucherRequest.getCustomerId(), deleteVoucherRequest.getVoucherId());
        return Response.OK;
    }

    public Response allocateVoucher(AllocateVoucherRequest request) {
        customerService.allocateVoucher(request.getCustomerId(), request.getVoucherId());

        return Response.OK;
    }

    public Response findAllBlockCustomers() {
        List<BlockCustomer> allBlockCustomers = customerService.findAllBlockCustomers();
        return new ListResponse(allBlockCustomers);
    }

    public Response createCustomer(CreateCustomerRequest request) {
        Customer customer = customerService.createCustomer(request.getEmail(), request.getName());
        return new StringResponse(customer);
    }

}
