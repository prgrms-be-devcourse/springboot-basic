package org.prgrms.query;

public enum VoucherSQL {
  INSERT("INSERT INTO voucher(id, type, amount) VALUES (?,?,?)"),
  FIND_ALL("SELECT * FROM voucher"),
  FIND_BY_ID("SELECT * FROM voucher WHERE id = ?"),
  DELETE_ALL("DELETE FROM voucher"),
  DELETE_BY_ID("DELETE FROM voucher WHERE id = ?");

  private final String value;

  VoucherSQL(String sql) {
    this.value = sql;
  }

  public String getSql() {
   return value;
  }
}
