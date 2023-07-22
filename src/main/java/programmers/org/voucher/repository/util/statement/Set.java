package programmers.org.voucher.repository.util.statement;

import static programmers.org.voucher.repository.util.constant.Symbol.*;
import static programmers.org.voucher.repository.util.constant.Symbol.COMMA;

public class Set extends Statement {
    private static final String SET = "SET";

    public Set(StringBuilder query) {
        this.query = query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();

        public Builder() {
            query.append(BLANK.getSymbol()).append(SET);
        }

        public Builder query(String column) {
            query.append(BLANK.getSymbol())
                    .append(column)
                    .append(EQUAL.getSymbol())
                    .append(QUESTION_MARK.getSymbol())
                    .append(COMMA.getSymbol());

            return this;
        }

        public Set build() {
            query.deleteCharAt(query.length() - 1);
            return new Set(query);
        }
    }
}
