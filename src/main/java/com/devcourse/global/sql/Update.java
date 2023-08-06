package com.devcourse.global.sql;

import com.devcourse.global.utils.CaseConverter;

import java.util.List;

public class Update implements Sql {
    private static final String UPDATE_FORMAT = "UPDATE %s SET %s";

    private final Class<?> table;
    private final List<String> conditions;
    private final Where where;

    private Update(Class<?> table, List<String> conditions, Where where) {
        this.table = table;
        this.conditions = conditions;
        this.where = where;
    }

    private Update(Class<?> table, List<String> conditions) {
        this(table, conditions, null);
    }

    public static UpdateCriteria builder() {
        return new UpdateCriteria();
    }

    public static class UpdateCriteria {
        private Class<?> table;

        private UpdateCriteria() {
        }

        public SetCriteria table(Class<?> table) {
            this.table = table;
            return new SetCriteria(table);
        }
    }

    public static class SetCriteria {
        private final Class<?> table;
        private List<String> conditions;

        private SetCriteria(Class<?> table) {
            this.table = table;
        }

        public WhereCriteria values(String... conditions) {
            this.conditions = List.of(conditions);
            return new WhereCriteria(table, this.conditions);
        }
    }

    public static class WhereCriteria {
        private final Class<?> table;
        private final List<String> conditions;
        private Where where;

        private WhereCriteria(Class<?> table, List<String> conditions) {
            this.table = table;
            this.conditions = conditions;
        }

        public Builder where(Where where) {
            this.where = where;
            return new Builder(table, conditions, where);
        }

        public Update build() {
            return new Update(table, conditions);
        }
    }


    public static class Builder {
        private final Class<?> table;
        private final List<String> conditions;
        private final Where where;

        public Builder(Class<?> table, List<String> conditions, Where where) {
            this.table = table;
            this.conditions = conditions;
            this.where = where;
        }

        public Update build() {
            return new Update(table, conditions, where);
        }
    }

    @Override
    public String getQuery() {
        String table = CaseConverter.toSnakeCase(this.table);
        String conditions = String.join(" = ?, ", this.conditions) + " = ?";
        String where = this.where == null ? "" : this.where.getQuery();

        return UPDATE_FORMAT.formatted(table, conditions) + where;
    }
}
