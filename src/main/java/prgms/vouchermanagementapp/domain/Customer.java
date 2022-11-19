package prgms.vouchermanagementapp.domain;

public class Customer {

    private Long id;
    private String customerName;

    public Customer() {
    }

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public Customer(long id, String customerName) {
        this.id = id;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }
}
