package com.devcourse.global.sql;

import com.devcourse.global.utils.CaseConverter;

public class Delete implements Sql {
    private static final String DELETE_FORMAT = "DELETE FROM %s";

    private final Class<?> from;
    private final Where where;

    private Delete(Class<?> from, Where where) {
        this.from = from;
        this.where = where;
    }

    public Delete(Class<?> from) {
        this(from, null);
    }

    public static DeleteCriteria builder() {
        return new DeleteCriteria();
    }

    public static class DeleteCriteria {
        private Class<?> from;

        private DeleteCriteria() {
        }

        public WhereCriteria from(Class<?> from) {
            this.from = from;
            return new WhereCriteria(from);
        }
    }

    public static class WhereCriteria {
        private final Class<?> from;
        private Where where;

        private WhereCriteria(Class<?> from) {
            this.from = from;
        }

        public Builder where(Where where) {
            this.where = where;
            return new Builder(from, where);
        }

        public Delete build() {
            return new Delete(from);
        }
    }

    public static class Builder {
        private final Class<?> from;
        private final Where where;

        private Builder(Class<?> from, Where where) {
            this.from = from;
            this.where = where;
        }

        public Delete build() {
            return new Delete(from, where);
        }
    }

    @Override
    public String getQuery() {
        String from = CaseConverter.toSnakeCase(this.from);
        String where = this.where == null ? "" : this.where.getQuery();
        return DELETE_FORMAT.formatted(from) + where;
    }
}
