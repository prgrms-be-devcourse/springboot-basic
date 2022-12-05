package org.prgrms.springorder.domain.customer.repository;

enum BlockCustomerSql {

    FIND_BY_ID("SELECT * FROM block_customers WHERE block_id  = :blockId"),
    INSERT(
        "INSERT INTO block_customers(block_id, customer_id, created_at) VALUES (:blockId, :customerId, :createdAt)"),
    FIND_ALL("SELECT * FROM block_customers"),
    DELETE_ALL("DELETE FROM block_customers");

    private final String sql;

    BlockCustomerSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
