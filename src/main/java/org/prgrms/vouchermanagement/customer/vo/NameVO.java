package org.prgrms.vouchermanagement.customer.vo;

public class NameVO {

  private String name;

  public NameVO(String name) {
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
