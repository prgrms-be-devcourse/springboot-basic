package programmers.org.voucher.repository.util.constant;

public enum Symbol {
    BLANK(" "),
    EQUAL("="),
    QUESTION_MARK("?"),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    COMMA(",")
    ;

    private final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
