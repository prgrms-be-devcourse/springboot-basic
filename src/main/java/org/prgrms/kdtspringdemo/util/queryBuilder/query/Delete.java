package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.queryBuilder.Entity;

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
        String tableName = this.table.getSimpleName().toLowerCase();
        String whereQuery = this.where == null ? "" : this.where.getQuery();

        return "DELETE FROM %s".formatted(tableName) + whereQuery;
    }

    public String getQuery() {
        return query;
    }

    public static DeleteCriteria builder() {
        return new DeleteCriteria();
    }

    public static class DeleteCriteria {
        private Class<? extends Entity> table;

        private DeleteCriteria() {
        }

        public WhereCriteria delete(Class<? extends Entity> entity) {
            this.table = entity;

            return new WhereCriteria(entity);
        }
    }

    public static class WhereCriteria {
        private final Class<? extends Entity> table;
        private Where where;

        private WhereCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public Builder where(Where where) {
            this.where = where;

            return new Builder(this.table, this.where);
        }

        public Delete build() {
            return new Delete(table);
        }
    }

    public static class Builder {
        private final Class<? extends Entity> table;
        private final Where where;

        private Builder(Class<? extends Entity> table, Where where) {
            this.table = table;
            this.where = where;
        }

        public Delete build() {
            return new Delete(table, where);
        }
    }
}
