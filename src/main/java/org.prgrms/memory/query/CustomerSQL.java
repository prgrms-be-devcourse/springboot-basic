package org.prgrms.memory.query;

public enum CustomerSQL {
  INSERT("INSERT INTO customer(id, name) VALUES (?,?)"),
  FIND_BY_ID("SELECT * FROM customer WHERE id = ?"),
  FIND_ALL("SELECT * FROM customer"),
  DELETE_ALL("DELETE FROM customer"),
  DELETE_BY_ID("DELETE FROM customer WHERE id = ?"),
  UPDATE("UPDATE customer SET name = ? WHERE id = ?");

  private final String value;

  CustomerSQL(String value) {
    this.value = value;
  }

  public String getSql() {
    return value;
  }
}
