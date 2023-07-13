package programmers.org.voucher.domain;

public class Customer {

    private Long id;

    private String name;

    private final String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer(Long customerId, String name, String email) {
        this.id = customerId;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
