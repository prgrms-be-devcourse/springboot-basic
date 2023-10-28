package devcourse.springbootbasic;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.util.UUIDUtil;

import java.util.UUID;

public class TestDataFactory {

    public static Customer generateBlacklistCustomer(String name) {
        return Customer.createCustomer(UUIDUtil.generateRandomUUID(), name, true);
    }

    public static Customer generateNotBlacklistCustomers(String name) {
        return Customer.createCustomer(UUIDUtil.generateRandomUUID(), name, false);
    }

    public static Voucher generateAssignedVoucher(VoucherType voucherType, long initialDiscountValue, UUID customerId) {
        return Voucher.createVoucher(UUIDUtil.generateRandomUUID(), voucherType, initialDiscountValue, customerId);
    }

    public static Voucher generateUnassignedVoucher(VoucherType voucherType, long initialDiscountValue) {
        return Voucher.createVoucher(UUIDUtil.generateRandomUUID(), voucherType, initialDiscountValue, null);
    }
}
