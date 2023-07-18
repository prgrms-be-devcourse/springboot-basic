package com.example.voucher.controller.request;

import java.util.UUID;
import com.example.voucher.constant.CustomerType;

public class CustomerRequest {

    public static class Create {

        private String name;
        private String email;
        private CustomerType customerType;

        public Create(String name, String email, CustomerType customerType) {
            this.name = name;
            this.email = email;
            this.customerType = customerType;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public CustomerType getCustomerType() {
            return customerType;
        }

    }

    public static class Update {

        private UUID customerId;
        private String name;
        private String email;
        private CustomerType customerType;

        public Update(UUID customerId, String name, String email, CustomerType customerType) {
            this.customerId = customerId;
            this.name = name;
            this.email = email;
            this.customerType = customerType;
        }

        public UUID getCustomerId() {
            return customerId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public CustomerType getCustomerType() {
            return customerType;
        }

    }

}
