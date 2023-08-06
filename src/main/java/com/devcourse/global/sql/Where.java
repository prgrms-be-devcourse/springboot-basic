package com.devcourse.global.sql;

public class Where implements Sql {
    static final String EMPTY = "";
    private static final int ZERO = 0;
    private static final String WHERE_FORMAT = " WHERE %s = ?";

    private final String where;
    private final int limit;

    private Where(String where, int limit) {
        this.where = where;
        this.limit = limit;
    }

    public Where(String where) {
        this(where, ZERO);
    }

    public static WhereCriteria builder() {
        return new WhereCriteria();
    }

    public static class WhereCriteria {
        private String where;

        private WhereCriteria() {
        }

        public LimitCriteria condition(String where) {
            this.where = where;
            return new LimitCriteria(where);
        }
    }

    public static class LimitCriteria {
        private final String where;
        private int limit;

        private LimitCriteria(String where) {
            this.where = where;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return new Builder(where, limit);
        }

        public Where build() {
            return new Where(where, ZERO);
        }
    }

    public static class Builder {
        private final String where;
        private final int limit;

        private Builder(String where, int limit) {
            this.where = where;
            this.limit = limit;
        }

        public Where build() {
            return new Where(where, limit);
        }
    }

    @Override
    public String getQuery() {
        String limit = this.limit == ZERO ? EMPTY : " LIMIT " + this.limit;
        return WHERE_FORMAT.formatted(where) + limit;
    }
}
