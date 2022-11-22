package org.prgrms.springorder.domain.customer.api;

import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.domain.customer.api.request.CreateCustomerRequest;
import org.prgrms.springorder.domain.customer.api.request.CustomerIdRequest;
import org.prgrms.springorder.domain.voucher.api.request.AllocateVoucherRequest;
import org.prgrms.springorder.domain.voucher.api.request.DeleteVoucherRequest;
import org.prgrms.springorder.global.exception.IllegalRequestException;
import org.springframework.stereotype.Component;

@Component
public class CustomerControllerDecorator {

    private final CustomerController customerController;

    public CustomerControllerDecorator(
        CustomerController customerController) {
        this.customerController = customerController;
    }

    public Response findAllVouchers(Request request) {
        if (request instanceof CustomerIdRequest) {
            return customerController.findAllVouchers((CustomerIdRequest) request);
        }

        throw new IllegalRequestException(CustomerIdRequest.class, request.getClass());
    }

    public Response deleteVoucher(Request request) {

        if (request instanceof DeleteVoucherRequest) {
            return customerController.deleteVoucher((DeleteVoucherRequest) request);
        }
        throw new IllegalRequestException(DeleteVoucherRequest.class, request.getClass());
    }

    public Response allocateVoucher(Request request) {
        if (request instanceof AllocateVoucherRequest) {
            return customerController.allocateVoucher((AllocateVoucherRequest) request);
        }
        throw new IllegalRequestException(AllocateVoucherRequest.class, request.getClass());
    }

    public Response findAllCustomers() {
        return customerController.findAllBlockCustomers();
    }

    public Response createCustomer(Request request) {
        if (request instanceof CreateCustomerRequest) {
            return customerController.createCustomer((CreateCustomerRequest) request);
        }

        throw new IllegalRequestException(CreateCustomerRequest.class, request.getClass());
    }
}
