package kr.co.programmers.springbootbasic.customer.repository;

public class CustomerQuery {
    public static final String CREATE_CUSTOMER
            = "INSERT INTO customer (id, name, status_id, wallet_id) VALUES (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?))";
    public static final String FIND_BY_CUSTOMER_ID = "SELECT * FROM customer WHERE id = UUID_TO_BIN(?)";
    public static final String FIND_ALL = "SELECT * FROM customer";
    public static final String UPDATE_CUSTOMER
            = "UPDATE customer SET status_id = ? WHERE id = UUID_TO_BIN(?)";
    public static final String DELETE_WALLET = "DELETE FROM wallet WHERE id = UUID_TO_BIN(?)";
    public static final String FIND_BY_VOUCHER_ID
            = "SELECT * FROM voucher AS v JOIN customer AS c ON v.wallet_id = c.wallet_id WHERE v.id = UUID_TO_BIN(?)";
    public static final String CREATE_WALLET = "INSERT INTO wallet (id) VALUES (UUID_TO_BIN(?))";
    public static final String DELETE_VOUCHER = "DELETE FROM voucher WHERE wallet_id = UUID_TO_BIN(?)";
    public static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = UUID_TO_BIN(?)";

    private CustomerQuery() {
    }
}
