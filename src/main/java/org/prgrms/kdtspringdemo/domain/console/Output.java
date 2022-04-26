package org.prgrms.kdtspringdemo.domain.console;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public String initMessage() {
        return """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type customer to register and see related data.
                Type voucher to manage vouchers.
                Type mapping to make customer choose voucher and manage it.
                Type BLACKLIST to see customers in Blacklist.
                """;
    }

    public String chooseVoucherTypeMessage() {
        return """
                === Choose Voucher ===
                1. FixedAmountVoucher(Fixed)
                2. PercentDiscountVoucher(Percent)
                Type Fixed if you want 1
                Type Percent if you want 2
                """;
    }

    public String chooseCustomerDMLMessage() {
        return """
                === This is Customer Part ===
                
                Type INSERT("Insert Customer"),
                Type UPDATE("Update Customer - no work"),
                Type COUNT("Count all customers"),
                Type FINDALL("Show all customers"),
                Type FINDBYID("Find customer by id - no work"),
                Type FINDBYNAME("Find customer by name - no work"),
                Type FINDBYEMAIL("Find customer by email - no work"),
                Type DELETEALL("delete all customer"),
                """;
    }

    public String FixedDiscountAmountMessage() {
        return """
                === Write Discount Amount ===
                Type over 0
                Type under 1,000,000
                """;
    }

    public String PercentDiscountAmountMessage() {
        return """
                === Write Percent Amount ===
                Type over 0
                Type under 100
                """;
    }

    public String chooseVoucherManagementMessage() {
        return """
                === This is Voucher Management Part ===
                
                Type UPDATE("Update voucher"),
                Type COUNT("Count all vouchers"),
                Type FINDALL("Show all vouchers"),
                Type FINDBYID("Find voucher by id - no work"),
                Type FINDBYTYPE("Find voucher by type - no work"),
                Type DELETEALL("delete all customer"),
                """;
    }

    public String initMappingMessage() {
        return """
                === This is mapping voucher and customer part ===
                
                Type ASSIGN("Assign voucher to the customer"),
                Type LISTASCUSTOMER("Inquire certain customer's voucher list"),
                Type DELETEVOUCHER("Delete the voucher of customer"),
                Type LISTASVOUCHER("Inquire certain voucher's customer")
                """;
    }
}
