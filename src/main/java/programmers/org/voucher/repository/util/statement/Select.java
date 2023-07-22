package programmers.org.voucher.repository.util.statement;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class Select extends Statement {
    private static final String SELECT = "SELECT";

    public Select(StringBuilder query) {
        this.query = query;
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
