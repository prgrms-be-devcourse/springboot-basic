package org.prgrms.kdt.Customers;

public class Customers {

    long num;
    String id;
    String name;

    public Customers(long num, String id, String name) {
        this.num = num;
        this.id = id;
        this.name = name;
    }

    public long getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
