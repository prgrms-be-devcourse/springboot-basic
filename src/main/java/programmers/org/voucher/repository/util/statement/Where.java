package programmers.org.voucher.repository.util.statement;

import static programmers.org.voucher.repository.util.constant.Symbol.*;

public class Where extends Statement {
    private static final String WHERE = "WHERE";

    public Where(StringBuilder query) {
        this.query = query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();

        public Builder() {
           query.append(BLANK.getSymbol()).append(WHERE);
        }

        public Builder query(String column) {
            query.append(BLANK.getSymbol())
                    .append(column)
                    .append(EQUAL.getSymbol())
                    .append(QUESTION_MARK.getSymbol())
                    .append(COMMA.getSymbol());

            return this;
        }

        public Where build() {
            query.deleteCharAt(query.length() - 1);
            return new Where(query);
        }
    }
}
