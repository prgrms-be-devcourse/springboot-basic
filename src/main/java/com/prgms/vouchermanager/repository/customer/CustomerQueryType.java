package com.prgms.vouchermanager.repository.customer;

public enum CustomerQueryType {

    INSERT("insert into customers (id, name, email, black_list ) values (:id, :name, :email, :black_list )"),
    UPDATE("update customers set id = :id , name = :name , email = :email , black_list = :black_list  where id = :id"),
    SELECT_BY_ID("select * from customers where id = :id"),
    SELECT_ALL("select * from customers"),
    DELETE_ALL("delete from customers"),
    DELETE_BY_ID("delete from customers where id = :id"),
    BLACKLIST("select * from customers where black_list=0");

    private final String query;

    CustomerQueryType(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
