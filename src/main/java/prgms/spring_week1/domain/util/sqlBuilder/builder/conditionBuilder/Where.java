package prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder;

public class Where {
    private StringBuilder where;

    public Where(StringBuilder whereBuilder) {
        where = whereBuilder;
    }

    public static class WhereBuilder {
        private StringBuilder whereBuilder = new StringBuilder();

        public WhereBuilder equal(String column, String value) {
            whereBuilder.append(" WHERE ").append(column).append(" = ").append(value);
            return this;
        }

        public WhereBuilder and(String column, String value) {
            whereBuilder.append(" AND ").append(column).append(" = ").append(value);
            return this;
        }

        public WhereBuilder or(String column, String value) {
            whereBuilder.append(" OR ").append(column).append(" = ").append(value);
            return this;
        }

        public Where build() {
            return new Where(whereBuilder);
        }
    }

    public String toString() {
        return where.toString();
    }
}
