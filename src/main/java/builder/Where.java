package builder;

import java.util.ArrayList;
import java.util.List;

public class Where {
	private final List<Condition> conditions;
	private final String query;

	private Where(List<Condition> conditions) {
		this.conditions = conditions;
		this.query = generateQuery();
	}

	private String generateQuery() {
		List<String> queries = new ArrayList<>();

		for (Condition condition : this.conditions) {
			String str = condition.getQuery();
			queries.add(str);
		}

		return String.join(" ", queries);
	}

	public String getQuery() {
		return this.query;
	}

	public static Builder builder(String column, Operator.Type operatorType, Object value) {
		return new Builder(column, operatorType, value);
	}

	public static class Builder {
		private final List<Condition> conditions = new ArrayList<>();

		private Builder(String column, Operator.Type operatorType, Object value) {
			Operator operator = new Operator(column, operatorType, value);
			Condition condition = new Condition(operator);
			this.conditions.add(condition);
		}

		public Builder and(String column, Operator.Type operatorType, Object value) {
			Operator operator = new Operator(column, operatorType, value);
			Condition condition = Condition.and(operator);
			conditions.add(condition);
			return this;
		}

		public Builder or(String column, Operator.Type operatorType, Object value) {
			Operator operator = new Operator(column, operatorType, value);
			Condition condition = Condition.or(operator);
			conditions.add(condition);
			return this;
		}

		public Where build() {
			return new Where(conditions);
		}
	}
}
