package kr.co.programmers.springbootbasic.customer.dto;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;

import java.text.MessageFormat;
import java.util.UUID;

public class CustomerResponse {
    private static final String CUSTOMER_FORMAT = """
            고객 아이디 : {0}
            고객 이름 : {1}
            고객 상태 : {2}
                        
            """;
    private final UUID id;
    private final String name;
    private final CustomerStatus status;
    private final UUID walletId;

    private CustomerResponse(UUID id, String name, CustomerStatus status, UUID walletId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.walletId = walletId;
    }

    public static CustomerResponse convertToCustomerResponse(Customer customer) {
        UUID id = customer.getId();
        String name = customer.getName();
        CustomerStatus status = customer.getStatus();
        UUID walletId = customer.getWalletId();

        return new CustomerResponse(id, name, status, walletId);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public String formatCustomerResponseDto() {
        return MessageFormat.format(CUSTOMER_FORMAT,
                this.id,
                this.name,
                this.status
        );
    }
}
