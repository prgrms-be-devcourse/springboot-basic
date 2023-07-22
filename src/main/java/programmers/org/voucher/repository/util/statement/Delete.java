package programmers.org.voucher.repository.util.statement;

import programmers.org.voucher.repository.util.constant.Table;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class Delete extends Statement {
    private static final String DELETE = "DELETE FROM";

    public Delete(StringBuilder query) {
        this.query = query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();

        public Builder() {
            query.append(DELETE);
        }

        public Builder query(Table table) {
            query.append(BLANK.getSymbol())
                    .append(table);

            return this;
        }

        public Delete build() {
            return new Delete(query);
        }
    }
}
