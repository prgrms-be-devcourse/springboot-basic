package com.devcourse.global.sql;

import com.devcourse.global.utils.CaseConverter;

import java.util.List;
import java.util.stream.Collectors;

public class Insert implements Sql {
    private static final String INSERT_FORMAT = "INSERT INTO %s(%s) VALUES(%s)";

    private final Class<?> into;
    private final List<String> values;

    private Insert(Class<?> into, List<String> values) {
        this.into = into;
        this.values = values;
    }

    public static InsertCriteria builder() {
        return new InsertCriteria();
    }

    public static class InsertCriteria {
        private Class<?> into;

        private InsertCriteria() {
        }

        public ValuesCriteria into(Class<?> into) {
            this.into = into;
            return new ValuesCriteria(into);
        }
    }

    public static class ValuesCriteria {
        private final Class<?> into;
        private List<String> values;

        private ValuesCriteria(Class<?> into) {
            this.into = into;
        }

        public Builder values(String... values) {
            this.values = List.of(values);
            return new Builder(into, this.values);
        }
    }

    public static class Builder {
        private final Class<?> into;
        private final List<String> values;

        private Builder(Class<?> into, List<String> values) {
            this.into = into;
            this.values = values;
        }

        public Insert build() {
            return new Insert(into, values);
        }
    }


    @Override
    public String getQuery() {
        String into = CaseConverter.toSnakeCase(this.into);
        String values = String.join(", ", this.values);
        String parameters = this.values.stream()
                .map(value -> "?")
                .collect(Collectors.joining(", "));

        return INSERT_FORMAT.formatted(into, values, parameters);
    }
}
