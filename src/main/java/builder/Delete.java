package builder;


import builder.builderEntity.Entity;

public class Delete {
    private final Class<? extends Entity> table;
    private final Where where;
    private final String query;

    private Delete(Class<? extends Entity> table, Where where) {
        this.table = table;
        this.where = where;
        this.query = generateQuery();
    }

    private Delete(Class<? extends Entity> table) {
        this(table, null);
    }

    private String generateQuery() {
        String whereQuery = where == null ? "" : "WHERE %s".formatted(where.getQuery());
        String tableName = this.table.getSimpleName().toLowerCase();
        return "DELETE FROM %s ".formatted(tableName) + whereQuery;
    }

    public String getQuery() {
        return this.query;
    }

    public static DeleteCriteria from(Class<? extends Entity> table) {
        return new DeleteCriteria(table);
    }

    public static class DeleteCriteria {
        private final Class<? extends Entity> table;

        private DeleteCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public OrderCriteria where(Where where) {
            return new OrderCriteria(table, where);
        }

        public Delete build() {
            return new Delete(table);
        }

    }

    public static class OrderCriteria {
        private final Class<? extends Entity> table;
        private Where where;

        public OrderCriteria(Class<? extends Entity> table, Where where) {
            this.table = table;
            this.where = where;
        }

        public Delete build() {
            return new Delete(this.table, this.where);
        }

    }
}
