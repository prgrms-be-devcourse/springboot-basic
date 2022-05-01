package org.prgrms.vouchermanagement.customer.vo;

public class Name {

  private String name;

  public Name(String name) {
    this.name = name;
  }

  public void changeName(String name) {
    validateName(name);
    this.name = name;
  }

  private void validateName(String name) {
    if (name.isBlank()) {
      throw new RuntimeException("Name should not be black");
    }
  }

  public String getName() {
    return name;
  }
}
