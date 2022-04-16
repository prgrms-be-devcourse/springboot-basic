package org.prgrms.springbootbasic.repository.customer;

public class CustomerDBConstString {

    public static final String SELECT_BY_ID = "select * from customers where customer_id = UUID_TO_BIN(?)";
    public static final String SELECT_ALL_SQL = "select * from customers";
    public static final String INSERT_SQL = "insert into customers(customer_id, name, email) values(UUID_TO_BIN(?), ?, ?)";
    public static final String DELETE_ALL_SQL = "delete from customers";
    public static final String UPDATE_BY_ID_SQL = "update customers set name = ? where customer_id = UUID_TO_BIN(?)";
    public static final String SELECT_BY_EMAIL = "select * from customers where email = ?";
    public static final String SELECT_BY_VOUCHER_TYPE_SQL =
        "select c.customer_id as customer_id, c.name as name, c.email as email"
            + " from vouchers v join customers c on v.customer_id = c.customer_id"
            + "  where v.type = ?";


    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    private CustomerDBConstString() {
        throw new AssertionError("CustomerDBConstString은 유틸성 클래스 입니다.");
    }
}
