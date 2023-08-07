package builder;

public class Operator {
	private final String column;
	private final Type type;
	private final Object value;
	private final String query;

	public Operator(String column, Type type, Object value) {
		this.column = column;
		this.type = type;
		this.value = value;
		this.query = generateQuery();
	}

	private String generateQuery() {
		return "%s %s %s".formatted(this.column, this.type.getSymbol(), this.value);
	}

	public String getQuery() {
		return this.query;
	}

	public enum Type {
		EQ("="),
		NEQ("!="),
		GT(">"),
		GTE(">="),
		LT("<"),
		LTE("<=");

		private final String symbol;

		Type(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
		}
	}
}
