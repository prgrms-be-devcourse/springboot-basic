package devcourse.springbootbasic;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.util.UUIDUtil;

public class TestDataFactory {

    public static Customer generateBlacklistCustomer(String name) {
        return Customer.createCustomer(UUIDUtil.generateRandomUUID(), name, true);
    }

    public static Customer generateNotBlacklistCustomers(String name) {
        return Customer.createCustomer(UUIDUtil.generateRandomUUID(), name, false);
    }
}
