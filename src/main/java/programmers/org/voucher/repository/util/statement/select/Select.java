package programmers.org.voucher.repository.util.statement.select;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class Select {
    private static final String SELECT = "SELECT";

    private StringBuilder query;

    public Select(StringBuilder query) {
        this.query = query;
    }

    public StringBuilder getQuery() {
        return query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();

        public Builder() {
            query.append(SELECT);
        }

        public Builder query(String column) {
            query.append(BLANK.getSymbol())
                    .append(column)
                    .append(",");

            return this;
        }

        public Select build() {
            query.deleteCharAt(query.length() - 1);
            return new Select(query);
        }
    }
}
