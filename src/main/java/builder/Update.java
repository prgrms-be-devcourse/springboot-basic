package builder;


import builder.builderEntity.Entity;

public class Update {
    private final Class<? extends Entity> table;
    private final Operator operator;
    private final Where where;
    private final String query;

    private Update(Class<? extends Entity> table, Operator operator, Where where) {
        this.table = table;
        this.operator = operator;
        this.where = where;
        this.query = generateQuery();
    }

    private Update(Class<? extends Entity> table, Operator operator) {
        this(table, operator, null);
    }

    private String generateQuery() {
        String tableName = this.table.getSimpleName().toLowerCase();
        String whereQuery = where == null ? "" : String.format(" WHERE %s", where.getQuery());
        return "UPDATE %s SET %s".formatted(tableName, operator.getQuery()) + whereQuery;
    }

    public String getQuery() {
        return this.query;
    }

    public static UpdateCriteria builder() {
        return new UpdateCriteria();
    }

    public static class UpdateCriteria {
        private Class<? extends Entity> table;

        public SetCriteria update(Class table) {
            this.table = table;
            return new SetCriteria(table);
        }
    }

    public static class SetCriteria {
        private final Class<? extends Entity> table;
        private Operator operator;

        private SetCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public WhereCriteria set(String column, Object value) {
            this.operator = new Operator(column, Operator.Type.EQ, value);
            return new WhereCriteria(table, operator);
        }
    }

    public static class WhereCriteria {
        private final Class<? extends Entity> table;
        private final Operator operator;
        private Where where;

        private WhereCriteria(Class<? extends Entity> table, Operator operator) {
            this.table = table;
            this.operator = operator;
        }

        public Builder where(Where where) {
            this.where = where;
            return new Builder(this.table, this.operator, this.where);
        }

        public Update build() {
            return new Update(table, operator);
        }
    }

    public static class Builder {
        private final Class<? extends Entity> table;
        private final Operator operator;
        private final Where where;

        private Builder(Class<? extends Entity> table, Operator operator, Where where) {
            this.table = table;
            this.operator = operator;
            this.where = where;
        }

        public Update build() {
            return new Update(table, operator, where);
        }
    }
}
