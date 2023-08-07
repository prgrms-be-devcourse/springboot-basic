package builder;

import java.util.ArrayList;
import java.util.List;


public class Order {
	private final List<Sort> sorts;
	private final String query;

	private Order(List<Sort> sorts) {
		this.sorts = sorts;
		this.query = generateQuery();
	}

	private String generateQuery() {
		List<String> queries = new ArrayList<>();

		for(Sort sort : sorts) {
			queries.add(sort.getQuery());
		}

		return String.join(", ", queries);
	}

	public String getQuery() {
		return this.query;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private final List<Sort> sorts  = new ArrayList<>();

		private Builder() {
		}

		public Builder asc(String column) {
			Sort asc = Sort.asc(column);
			sorts.add(asc);
			return this;
		}

		public Builder desc(String column) {
			Sort desc = Sort.desc(column);
			sorts.add(desc);
			return this;
		}

		public Order build() {
			return new Order(sorts);
		}
	}
}
