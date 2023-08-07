package builder;

public class Condition {
	private static final String OR = "OR";
	private static final String AND = "AND";

	private final String logicOperation;
	private final Operator operator;
	private final String query;

	public Condition(Operator operator) {
		this(null, operator);
	}

	private Condition(String logicOperation, Operator operator) {
		this.logicOperation = logicOperation;
		this.operator = operator;
		this.query = generateQuery(logicOperation, operator);
	}

	private String generateQuery(String logicOperation, Operator operator) {
		String logicOperationText = logicOperation == null ? "" : logicOperation;
		return "%s %s".formatted(logicOperationText, operator.getQuery()).trim();
	}

	public String getQuery() {
		return this.query;
	}

	public static Condition or(Operator operator) {
		return new Condition(OR, operator);
	}

	public static Condition and(Operator operator) {
		return new Condition(AND, operator);
	}

}
