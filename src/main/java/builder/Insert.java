package builder;


import builder.builderEntity.Entity;

import java.util.List;
import java.util.Map;

public class Insert {
	private final Class<? extends Entity> table;
	private Map<String, Object> values;
	private final String query;

	public Insert(Class<? extends Entity> table, Map<String, Object> values) {
		this.table = table;
		this.values = values;
		this.query = generateQuery();
	}

	private String generateQuery() {
		List<String> columnSet = this.values.entrySet()
				.stream()
				.map(Map.Entry::getKey)
				.toList();

		List<String> valueSet = this.values.entrySet()
				.stream()
				.map(Map.Entry::getValue)
				.map(value -> {
					if (value instanceof String) {
						return String.format("\'%s\'", value);
					}

					return value.toString();
				})
				.toList();

		String columns = String.join(", ", columnSet);
		String values = String.join(", ", valueSet);
		String tableName = this.table.getSimpleName().toLowerCase();
		return "INSERT INTO %s (%s) VALUES (%s)".formatted(tableName, columns, values);
	}

	public String getQuery() {
		return this.query;
	}

	public static InsertCriteria into(Class<? extends Entity> table) {
		return new InsertCriteria(table);
	}

	public static class InsertCriteria {
		private final Class<? extends Entity> table;

		private InsertCriteria(Class<? extends Entity> table) {
			this.table = table;
		}

		public Insert values(Map<String, Object> values) {
			return new Insert(this.table, values);
		}
	}
}
