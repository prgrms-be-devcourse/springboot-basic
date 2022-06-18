package org.devcourse.voucher.application.customer.controller.dto;

public class CustomerRequest {

  private String name;

  private String email;

  public CustomerRequest() {
  }

  public CustomerRequest(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

}
