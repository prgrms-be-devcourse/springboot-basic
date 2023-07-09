package co.programmers.voucher_management.common;

public enum Status {
	NORMAL("Y"),
	DELETED("N");
	private final String symbol;

	Status(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
