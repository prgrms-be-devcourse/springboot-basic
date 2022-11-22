package org.prgrms.springorder.console.io;

import static org.prgrms.springorder.console.core.Command.BLACKLIST;
import static org.prgrms.springorder.console.core.Command.CREATE;
import static org.prgrms.springorder.console.core.Command.CREATE_CUSTOMER;
import static org.prgrms.springorder.console.core.Command.DELETE_CUSTOMER_VOUCHERS;
import static org.prgrms.springorder.console.core.Command.GET_CUSTOMER_VOUCHERS;
import static org.prgrms.springorder.console.core.Command.GET_VOUCHER_WITH_CUSTOMER;
import static org.prgrms.springorder.console.core.Command.LIST;
import static org.prgrms.springorder.console.core.Command.POST_CUSTOMER_VOUCHERS;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import org.prgrms.springorder.console.core.Command;
import org.prgrms.springorder.domain.customer.api.request.CreateCustomerRequest;
import org.prgrms.springorder.domain.customer.api.request.CustomerIdRequest;
import org.prgrms.springorder.domain.voucher.api.VoucherIdRequest;
import org.prgrms.springorder.domain.voucher.api.request.AllocateVoucherRequest;
import org.prgrms.springorder.domain.voucher.api.request.DeleteVoucherRequest;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRequestBuilder {

    private final Map<Command, Supplier<Request>> requestMap;

    public ConsoleRequestBuilder(Console console) {
        requestMap = new EnumMap<>(Command.class);

        requestMap.put(CREATE, () -> getVoucherCreateRequest(console));
        requestMap.put(LIST, () -> null);
        requestMap.put(BLACKLIST, () -> null);
        requestMap.put(POST_CUSTOMER_VOUCHERS, () -> inputAllocationVoucherRequest(console));
        requestMap.put(GET_CUSTOMER_VOUCHERS, () -> inputCustomerIdRequest(console));
        requestMap.put(DELETE_CUSTOMER_VOUCHERS, () -> inputDeleteVoucherRequest(console));
        requestMap.put(GET_VOUCHER_WITH_CUSTOMER, () -> inputVoucherIdRequest(console));
        requestMap.put(CREATE_CUSTOMER, () -> createCustomerRequest(console));
    }

    public Request request(Command command) {
        return requestMap.get(command).get();
    }

    private VoucherCreateRequest getVoucherCreateRequest(Console console) {
        console.showMessage("바우처 타입을 입력하세요. 'fixed' or 'percent' : ");
        String inputVoucherType = console.input();
        console.showMessage("할인 금액을 입력하세요. : ");
        long discountAmount = console.inputStringToLong();
        VoucherType voucherType = VoucherType.of(inputVoucherType);

        return VoucherCreateRequest.of(voucherType, discountAmount);
    }

    private Request inputVoucherIdRequest(Console console) {
        console.showMessage("바우처 Id를 입력하세요 : ");
        String input = console.input();

        return new VoucherIdRequest(input);
    }

    private Request inputCustomerIdRequest(Console console) {
        console.showMessage("고객의 Id를 입력하세요. : ");
        String input = console.input();
        return new CustomerIdRequest(input);
    }

    private Request inputAllocationVoucherRequest(Console console) {
        console.showMessage("바우처 Id를 입력하세요 : ");
        String voucherIdStr = console.input();
        console.showMessage("고객의 Id를 입력하세요 : ");
        String customerIdStr = console.input();

        return new AllocateVoucherRequest(voucherIdStr, customerIdStr);
    }

    private Request inputDeleteVoucherRequest(Console console) {
        console.showMessage("바우처 Id를 입력하세요 : ");
        String voucherIdStr = console.input();
        console.showMessage("고객의 Id를 입력하세요 : ");
        String customerIdStr = console.input();
        return null;
//        return new DeleteVoucherRequest(voucherIdStr, customerIdStr);
    }

    private Request createCustomerRequest(Console console) {
        console.showMessage("고객의 이름을 입력하세요. : ");
        String name = console.input();
        console.showMessage("고객의 이메일을 입력하세요 : ");
        String email = console.input();

        return new CreateCustomerRequest(name, email);
    }

}
