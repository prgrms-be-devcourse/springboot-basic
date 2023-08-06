package com.devcourse.global.sql;

import com.devcourse.global.utils.CaseConverter;
import com.devcourse.voucher.domain.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Select implements Sql {
    private static final String SELECT_FORMAT = "SELECT %s FROM %s";

    private final List<String> fields;
    private final Class<?> from;
    private final Where where;

    public Select(List<String> fields, Class<?> from, Where where) {
        this.fields = fields;
        this.from = from;
        this.where = where;
    }

    public Select(List<String> fields, Class<?> from) {
        this(fields, from, null);
    }

    public static SelectCriteria builder() {
        return new SelectCriteria();
    }

    public static class SelectCriteria {
        private List<String> fields;

        private SelectCriteria() {
        }

        public FromCriteria select(String... fields) {
            this.fields = List.of(fields);

            return new FromCriteria(this.fields);
        }

        public WhereCriteria select(Class<?> entity) {
            this.fields = Arrays.stream(entity.getDeclaredFields())
                    .filter(field -> !field.isAnnotationPresent(Transient.class))
                    .map(field -> CaseConverter.toSnakeCase(field.getName()))
                    .toList();

            return new WhereCriteria(fields, entity);
        }
    }

    public static class FromCriteria {
        private final List<String> fields;
        private Class<?> from;

        private FromCriteria(List<String> fields) {
            this.fields = fields;
        }

        public WhereCriteria from(Class<?> from) {
            this.from = from;
            return new WhereCriteria(this.fields, from);
        }
    }

    public static class WhereCriteria {
        private final List<String> fields;
        private final Class<?> from;
        private Where where;

        private WhereCriteria(List<String> fields, Class<?> from) {
            this.fields = fields;
            this.from = from;
        }

        public Builder where(Where where) {
            this.where = where;
            return new Builder(fields, from, where);
        }

        public Select build() {
            return new Select(fields, from);
        }
    }

    public static class Builder {
        private final List<String> fields;
        private final Class<?> from;
        private final Where where;

        private Builder(List<String> fields, Class<?> from, Where where) {
            this.fields = fields;
            this.from = from;
            this.where = where;
        }

        public Select build() {
            return new Select(fields, from, where);
        }
    }

    @Override
    public String getQuery() {
        String fields = String.join(", ", this.fields);
        String table = CaseConverter.toSnakeCase(this.from);
        String where = this.where != null ? this.where.getQuery() : Where.EMPTY;

        return SELECT_FORMAT.formatted(fields, table) + where;
    }
}
