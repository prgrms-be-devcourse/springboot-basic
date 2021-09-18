package org.prgrms.devcourse;

import org.prgrms.devcourse.blackuser.BlackUser;
import org.prgrms.devcourse.customer.Customer;
import org.prgrms.devcourse.blackuser.BlackUserService;
import org.prgrms.devcourse.customer.CustomerService;
import org.prgrms.devcourse.voucher.VoucherService;
import org.prgrms.devcourse.ui.UserInterface;
import org.prgrms.devcourse.voucher.Voucher;
import org.prgrms.devcourse.voucher.VoucherType;

import java.util.*;

import static org.prgrms.devcourse.voucher.VoucherType.FIXED_AMOUNT_DISCOUNT_VOUCHER;
import static org.prgrms.devcourse.voucher.VoucherType.PERCENT_DISCOUNT_VOUCHER;


public class VoucherProgram {
    private VoucherService voucherService;
    private BlackUserService blackUserService;
    private CustomerService customerService;
    private UserInterface userInterface;


    public VoucherProgram(VoucherService voucherService,
                          BlackUserService blackUserService,
                          CustomerService customerService,
                          UserInterface userInterface) {
        this.voucherService = voucherService;
        this.blackUserService = blackUserService;
        this.customerService = customerService;
        this.userInterface = userInterface;
    }

    public void run() {
        String userInput = null;
        while (true) {
            userInterface.showVoucherProgramMenuInterface();
            userInput = userInterface.input();

            switch (VoucherProgramMenu.findByUserInput(userInput)) {
                case CREATE -> {
                    userInterface.showVoucherTypeSelectMessage();
                    userInput = userInterface.input();
                    userInterface.showVoucherDiscountValueInputMessage();
                    createDiscreteVoucher(userInput);
                }
                case LIST -> {
                    List<Voucher> voucherList = voucherService.getVoucherList();
                    userInterface.showVoucherList(voucherList);
                }
                case BLACK_LIST -> {
                    List<BlackUser> blackList = blackUserService.getBlackUserList();
                    userInterface.showBlackList(blackList);
                }
                case CUSTOMER_LIST -> {
                    List<Customer> customerList = customerService.getAllCustomers();
                    userInterface.showCustomerList(customerList);
                }
                case EXIT -> {
                    userInterface.showVoucherProgramTerminateMessage();
                    return;
                }
                default -> userInterface.showInvalidInputMessage();
            }
        }
    }

    private void createDiscreteVoucher(String voucherTypeNumber) {
        String discountValue = userInterface.input();
        switch (VoucherType.findByTypeNumber(voucherTypeNumber)) {
            case FIXED_AMOUNT_DISCOUNT_VOUCHER -> voucherService.createVoucher(Voucher.of(UUID.randomUUID(), Long.parseLong(discountValue), FIXED_AMOUNT_DISCOUNT_VOUCHER));
            case PERCENT_DISCOUNT_VOUCHER -> voucherService.createVoucher(Voucher.of(UUID.randomUUID(), Long.parseLong(discountValue), PERCENT_DISCOUNT_VOUCHER));
            default -> userInterface.showInvalidInputMessage();
        }
    }
}
