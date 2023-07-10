package com.programmers.springbasic.domain.customer.view;

import com.programmers.springbasic.domain.customer.dto.response.CustomerResponseDTO;

public class CustomerInfoResponse {
    CustomerResponseDTO customerResponseDTO;

    public CustomerInfoResponse(CustomerResponseDTO customerResponseDTO) {
        this.customerResponseDTO = customerResponseDTO;
    }

    public String getCustomerInfoView() {
        StringBuilder sb = new StringBuilder();

        sb.append(CustomerInfoMessage.CUSTOMER_ID_INFO_MESSAGE.getInfoMessage() + customerResponseDTO.getCustomerId() + "\n")
                .append(CustomerInfoMessage.CUSTOMER_NAME_INFO_MESSAGE.getInfoMessage() + customerResponseDTO.getName() + "\n")
                .append(CustomerInfoMessage.CUSTOMER_EMAIL_INFO_MESSAGE.getInfoMessage() + customerResponseDTO.getEmail() + "\n");

        return sb.toString();
    }
}
