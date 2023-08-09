package prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder;

import static prgms.spring_week1.domain.util.sqlBuilder.LastIndexLength.INVALID_LAST_COMMA;

public class Values {
    private StringBuilder values;

    public Values(StringBuilder valuesBuilder) {
        this.values = valuesBuilder;
    }

    public static ValuesBuilder builder(){
        return new ValuesBuilder();
    }

    public static class ValuesBuilder {
        private StringBuilder valuesBuilder = new StringBuilder();

        public ValuesBuilder values(String... values) {
            valuesBuilder.append(" VALUES" + " " + "(");

            for (String value : values) {
                valuesBuilder.append(value + ",");
            }

            valuesBuilder.setLength(valuesBuilder.length() - INVALID_LAST_COMMA);
            valuesBuilder.append(")");

            return this;
        }

        public Values build() {
            return new Values(valuesBuilder);
        }
    }

    public String getQuery() {
        return values.toString();
    }
}

