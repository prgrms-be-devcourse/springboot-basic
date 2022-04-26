package org.prgrms.springbasic.utils.sql;

public enum CustomerSQL {

    CREATE_CUSTOMER(
            "insert into " +
                    "customers(customer_id, customer_type, name, created_at, modified_at) " +
                    "values(UUID_TO_BIN(:customerId), :customerType, :name, :createdAt, :modifiedAt)"
    ),
    SELECT_BY_CUSTOMER_ID(
            "select * from customers " +
                    "where customer_id = UUID_TO_BIN(:customerId)"
    ),
    SELECT_BY_VOUCHER_ID(
            "select * from customers c left outer join vouchers v " +
                    "on c.customer_id = v.customer_id " +
                    "where v.voucher_id = UUID_TO_BIN(:voucherId)"
    ),
    SELECT_CUSTOMERS(
            "select * from customers order by created_at"
    ),
    SELECT_COUNT(
            "select count(*) from customers"
    ),
    UPDATE_CUSTOMER(
            "update customers set " +
                    "customer_type = :customerType, " +
                    "name = :name, " +
                    "modified_at = :modifiedAt " +
                    "where customer_id = UUID_TO_BIN(:customerId)"
    ),
    DELETE_BY_CUSTOMER_ID(
            "delete from customers" +
                    " where customer_id = UUID_TO_BIN(:customerId)"
    ),
    DELETE_CUSTOMERS(
            "delete from customers"
    );

    private final String query;

    CustomerSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
