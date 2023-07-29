package prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder;

public class Set {
    private StringBuilder set;

    public Set(StringBuilder set) {
        this.set = set;
    }

    public static class SetBuilder {
        private StringBuilder setBuilder = new StringBuilder();

        public SetBuilder set(String column, String value) {
            setBuilder.append(column).append(" = ").append(value);
            return this;
        }

        public Set build() {
            return new Set(setBuilder);
        }
    }

    public String toString() {
        return set.toString();
    }
}
